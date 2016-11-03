package org.wrl.elasticsearch.natives;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.AvgBuilder;
import org.junit.Before;
import org.junit.Test;
import org.wrl.elasticsearch.utils.JsonMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2014-09-23 16:12
 */
public class NativeElasticSearchClient {
    protected static Client client = null;

    @Before
    public void setUp() {
        /*
         * 创建客户端，所有的操作都由客户端开始，这个就好像是JDBC的Connection对象
         * 用完记得要关闭
         */
        client = TransportClient.builder().settings(getSettings())
                .build()
                .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("localhost", 9300) ));
    }

    @Test
    public void testAggregation() {
        String keyword = "商务";
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("cnKeywords", keyword).analyzer("ik_smart_syno").operator(MatchQueryBuilder.Operator.AND);

        TermsBuilder termsBuilder =
                AggregationBuilders.terms("popular_colors").field("color").showTermDocCountError(true);

        AvgBuilder avgAggregation = AggregationBuilders.avg("avg_price").field("price");
        termsBuilder.subAggregation(avgAggregation);

        SearchRequestBuilder requestBuilder = client.prepareSearch("cars")
                //.setQuery(queryBuilder)
                .addAggregation(termsBuilder)
                .setSize(0);

        System.out.println(requestBuilder.toString());

        SearchResponse response = requestBuilder.execute().actionGet();

        Aggregations aggregations = response.getAggregations();
        System.out.println(response.getHits().getTotalHits());

        aggregations.asList().stream().filter(aggregation -> aggregation != null).forEach(aggregation -> {
//            System.out.println(JsonMapper.nonEmptyMapper().toJson(DefaultAggregationMapper.parse(aggregation)));
        });
        //Map<String, Aggregation> map = aggregations.asMap();

        //Iterator<Aggregation> iterator = aggregations.iterator();
        //while (iterator.hasNext()) {
        //    Aggregation aggregation = iterator.next();
        //    System.out.println(JsonMapper.nonEmptyMapper().toJson(aggregation));
        //}
    }

    @Test
    public void testCount() {
        String keyword = "商务";
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("cnKeywords", keyword).analyzer("ik_smart_syno").operator(MatchQueryBuilder.Operator.AND);

        AbstractAggregationBuilder aggregationBuilder =
                AggregationBuilders.stats("years").field("createTime");

        AbstractAggregationBuilder termsAggregation =
                AggregationBuilders.terms("words").field("brandId").showTermDocCountError(true);
        AbstractAggregationBuilder termsAggregation2 =
                AggregationBuilders.terms("saleFormat").field("saleFormat").showTermDocCountError(true);


        AbstractAggregationBuilder valueAggregation =
                AggregationBuilders.count("value_count").field("saleFormat");

        SearchResponse response = client.prepareSearch("product_idx")
                .setQuery(queryBuilder)
                .addAggregation(aggregationBuilder)
                .addAggregation(termsAggregation)
                .addAggregation(termsAggregation2)
                .addAggregation(valueAggregation)
                .setSize(0)
                .execute().actionGet();

        Aggregations aggregations = response.getAggregations();
        System.out.println(response.getHits().getTotalHits());
        Map<String, Aggregation> map = aggregations.asMap();
        System.out.println(JsonMapper.nonEmptyMapper().toJson(map));
    }

    /**
     * Java API批量导出数据到CSV中
     */
    @Test
    public void testExportToCSV() {
        SearchResponse response = client.prepareSearch("store_api_log").setTypes("API_OPERATE_LOG")
                .setQuery(QueryBuilders.termQuery("apiAccountId", "577c6983eb86081f7cd4d930")).setSize(10000).setScroll(new TimeValue(600000))
                .setSearchType(SearchType.SCAN).execute().actionGet();
        //setSearchType(SearchType.Scan) 告诉ES不需要排序只要结果返回即可 setScroll(new TimeValue(600000)) 设置滚动的时间

        String scrollid = response.getScrollId();

        //把导出的结果以JSON的格式写到文件里
        Path esFilePath = Paths.get("d:/es.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(esFilePath, StandardCharsets.UTF_8, StandardOpenOption.APPEND)){

            //每次返回数据10000条。一直循环查询直到所有的数据都查询出来
            while (true) {
                SearchResponse response2 = client.prepareSearchScroll(scrollid).setScroll(new TimeValue(1000000)).execute().actionGet();
                SearchHits searchHit = response2.getHits();
                //再次查询不到数据时跳出循环
                if (searchHit.getHits().length == 0) {
                    break;
                }
                System.out.println("查询数量 ：" + searchHit.getHits().length);
                for (int i = 0; i < searchHit.getHits().length; i++) {
                    String json = searchHit.getHits()[i].getSourceAsString();
                    writer.write(json);
                    writer.write("\r\n");
                }
            }
            System.out.println("查询结束");

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    /**
     * Java API批量导入数据到Elasticsearch中
     */
    @Test
    public void testImportToES() {
        //读取刚才导出的ES数据
        try (BufferedReader br = new BufferedReader(new FileReader("d:/es.txt"))){

            String json = null;
            int count = 0;
            //开启批量插入
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            while ((json = br.readLine()) != null) {
                bulkRequest.add(client.prepareIndex("cars", "transactions").setSource(json));
                //每一千条提交一次
                if (count% 1000==0) {
                    bulkRequest.execute().actionGet();
                    System.out.println("提交了：" + count);
                }
                count++;
            }
            bulkRequest.execute().actionGet();
            System.out.println("插入完毕");
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    protected static Settings getSettings() {
        Map<String, Object> settingsMap = new HashMap<>();
        //指定集群名称
        settingsMap.put("cluster.name", "elasticsearch");
        //探测集群中机器状态
        settingsMap.put("client.transport.sniff", "true");
        //5个主分片
        settingsMap.put("number_of_shards", "5");
        //测试环境，减少副本提高速度
        settingsMap.put("number_of_replicas", "0");

        Settings settings = Settings.settingsBuilder().put(settingsMap).build();

        return settings;
    }
}

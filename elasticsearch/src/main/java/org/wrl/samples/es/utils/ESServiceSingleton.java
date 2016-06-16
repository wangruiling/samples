package org.wrl.samples.es.utils;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;

import java.lang.reflect.Constructor;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2014-08-25 14:19
 */
public class ESServiceSingleton {
    private static String clusterName = "elasticsearch";
    private static Map<String, String> m = new HashMap<String, String>();
    // 设置client.transport.sniff为true来使客户端去嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中，这样做的好处是一般你不用手动设置集群里所有集群的ip到连接客户端，它会自动帮你添加，并且自动发现新加入集群的机器。
    //static Settings settings = ImmutableSettings.settingsBuilder().put(m).put("cluster.name",clusterName).put("client.transport.sniff", true).build();

    // 创建私有对象
    private static TransportClient client;

    static {
        try {
            Class<?> clazz = Class.forName(TransportClient.class.getName());
            Constructor<?> constructor = clazz.getDeclaredConstructor(Settings.class);
            constructor.setAccessible(true);
            // 它需要指定es集群中其中一台或多台机的ip地址和端口
            //client = (TransportClient) constructor.newInstance(settings);
            client.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("192.168.1.8", 9300)))
                    .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("192.168.1.8", 9300)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void tes() {
        SearchResponse response = client.prepareSearch("index1", "index2")
                .setTypes("type1", "type2")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery("multi", "test"))                 // Query
                .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();
    }

    // 取得实例
    public static synchronized TransportClient getTransportClient() {
        return client;
    }

    public static void closeClient() {
        client.prepareSearch("");
//        SearchRequest searchRequest = SearchSourceBuilder.searchRequest()
//                .source(searchSource().query(new FunctionScoreQueryBuilder(matchQuery("party_id", "12"))
//                        .add(termsFilter("course_cd", Arrays.asList("writ100", "writ112", "writ113")), factorFunction(3.0f))));
        client.close();
    }
}

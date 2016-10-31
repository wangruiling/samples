package org.wrl.elasticsearch.natives.admin.indices;

import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;
import org.wrl.elasticsearch.natives.AbstractApi;

import java.io.IOException;

/**
 * Created by wangrl on 2016/10/31.
 */
public class IndicesTest extends AbstractApi {

    /**
     * 创建索引
     * @throws IOException
     */
    @Test
    public void testCreate() throws IOException {
        Client client = getClient();
        CreateIndexResponse response = client.admin().indices()
                .prepareCreate("news")
//                .setSettings(ImmutableSettings.settingsBuilder().put("number_of_shards", 1))
                .addMapping("news", XContentFactory.jsonBuilder()
                        .startObject()
                        .startObject("news")
                        .startObject("properties")
                        .startObject("title")
                        .field("analyzer", "whitespace")
                        .field("type", "string")
                        .endObject()
                        .endObject()
                        .endObject()
                        .endObject())
                .execute().actionGet();

        System.out.println("Ack: " + response.isAcknowledged());
    }


    /**
     * 关闭索引
     * @throws IOException
     */
    @Test
    public void testClose() throws IOException {
        recreateIndex("library");
        createSomeBooks("library");

        Client client = getClient();
        CloseIndexResponse response = client.admin().indices()
                .prepareClose("library")
                .execute().actionGet();

        System.out.println("Ack: " + response.isAcknowledged());
    }

    /**
     * 打开索引
     * @throws IOException
     */
    @Test
    public void testOpen() throws IOException {
        recreateIndex("library");

        Client client = getClient();
        OpenIndexResponse response = client.admin().indices()
                .prepareOpen("library")
                .execute().actionGet();

        System.out.println("Ack: " + response.isAcknowledged());

    }

    /**
     * 刷新索引
     * @throws IOException
     */
    @Test
    public void testRefresh() throws IOException {
        recreateIndex("library");

        Client client = getClient();
        RefreshResponse response = client.admin().indices()
                .prepareRefresh("library")
                .execute().actionGet();

        System.out.println("Total Shards: " + response.getTotalShards());
    }

    @Test
    public void test() throws IOException {
        recreateIndex("library");

        Client client = getClient();
//        OptimizeResponse response = client.admin().indices()
//                .prepareOptimize("library")
//                .setMaxNumSegments(2)
//                .setOnlyExpungeDeletes(false)
//                .setFlush(true)
//                .execute().actionGet();

//        System.out.println("Total Shards: " + response.getTotalShards());
    }

    /**
     * 设置映射，下面给news索引的news类型建立一个映射
     * @throws IOException
     */
    @Test
    public void testPutMapping() throws IOException {
        recreateIndex("news");

        Client client = getClient();
        PutMappingResponse response = client.admin().indices()
                .preparePutMapping("news")
                .setType("news")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .startObject("news")
                        .startObject("properties")
                        .startObject("title")
                        .field("analyzer", "whitespace")
                        .field("type", "string")
                        .endObject()
                        .endObject()
                        .endObject()
                        .endObject())
                .execute().actionGet();

        System.out.println("ACK: " + response.isAcknowledged());
    }


    /**
     * 清空缓存区
     * @throws IOException
     */
    @Test
    public void testFlush() throws IOException {
        recreateIndex("library");
        Client client = getClient();
        FlushResponse response = client.admin().indices()
                .prepareFlush("library")
//                .setFull(false)
                .execute().actionGet();

        System.out.println("Total Shards: " + response.getTotalShards());
    }

    /**
     * 删除索引
     * @throws IOException
     */
    @Test
    public void testDelete() throws IOException {
        recreateIndex("news");

        Client client = getClient();
        DeleteIndexResponse response = client.admin().indices()
                .prepareDelete("news")
                .execute().actionGet();

        System.out.println("Ack: " + response.isAcknowledged());
    }


    /**
     * 分析api在查看指定分析器、分词器和过滤器的分析处理过程时非常有用。
     * @throws IOException
     */
    @Test
    public void testAnalyze() throws IOException {
        recreateIndex("library");
        createSomeBooks("library");

        Client client = getClient();

        AnalyzeResponse response = client.admin().indices()
                .prepareAnalyze("library", "ElasticSearch Servers")
                //You can sen analyzer OR tokenizer/token filters: .setAnalyzer("standard")
                .setTokenizer("whitespace")
                .setTokenFilters("nGram")
                .execute().actionGet();

        System.out.println("Terms:");
        for (AnalyzeResponse.AnalyzeToken analyzeToken : response.getTokens()) {
            System.out.println("\t" + analyzeToken.getTerm());
        }

    }
}

package org.wrl.elasticsearch.natives.admin.indices;

import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
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
    public void test() throws IOException {
        recreateIndex("library");

        Client client = getClient();
        RefreshResponse response = client.admin().indices()
                .prepareRefresh("library")
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
}

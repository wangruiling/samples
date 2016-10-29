package org.wrl.elasticsearch.natives;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.index.IndexAction;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.junit.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by wangrl on 2016/10/28.
 */
public class AbstractApi {
    protected static final Logger LOG = LoggerFactory.getLogger("TEST");

    private Client client;
    private Node node;


    /**
     * Returns ElasticSearch client.
     * @return client
     */
    public Client getClient() {
        if (this.client != null) {
            return this.client;
        }

        NodeBuilder builder = NodeBuilder.nodeBuilder();

        Settings settings = Settings.builder()
//                .put("http.enabled", "false")
//                .put("path.data", "target/elasticsearchTestData")
                .put("path.home", "D:/tools/elasticsearch")
                .put("gateway.type", "default")
                .build();

        //local(true)，Elasticsearch可以在当前虚拟机内作为一个独立节点而存在
        builder.settings(settings).local(true).data(true);

        this.node = builder.node();

        //客户端节点，不会持有数据
        this.client = node.client();

        return this.client;
    }

    /**
     * 创建一个独立的本地节点集群
     * @param clusterName
     */
    public void createLocalCluster(final String clusterName) {
        NodeBuilder builder = NodeBuilder.nodeBuilder();

        Settings settings = Settings.builder()
                .put("gateway.type", "default")
                .put("cluster.name", "escluster2")
                .put("path.home", "D:/tmp")
                .build();

        builder.settings(settings).local(false).data(true);

        this.node = builder.node();
        this.client = node.client();
    }

    /**
     * Create index with given name if index doesn't exist.
     * @param index index name
     */
    public void createIndexIfNeeded(final String index) {
        if (!existsIndex(index)) {
            getClient().admin().indices().prepareCreate(index).execute().actionGet();
        }
    }

    public void recreateIndex(final String index) {
        logger("Recreting index: " + index);
        if (existsIndex(index)) {
            getClient().admin().indices().prepareDelete(index).execute().actionGet();
        }
        getClient().admin().indices().prepareCreate(index).execute().actionGet();
    }

    public boolean existsIndex(final String index) {
        IndicesExistsResponse response = getClient().admin().indices().prepareExists(index).execute().actionGet();
        return response.isExists();
    }

    protected void logger(final String msg) {
        LOG.info("* " + msg);
    }

    @After
    public void close() {
        if(client != null) {
            client.close();
        }

        if (node != null) {
            node.close();
        }
    }

    protected void createSomeBooks(final String index) throws IOException {
        getClient().prepareBulk()
                .add(new IndexRequestBuilder(getClient(), IndexAction.INSTANCE, "library").setId("1").setType("book").setSource(
                        XContentFactory.jsonBuilder()
                                .startObject()
                                .field("title", "Book A Title")
                                .field("num", 1)
                                .endObject()
                ).request())
                .add(new IndexRequestBuilder(getClient(), IndexAction.INSTANCE, "library").setType("book").setSource(
                        XContentFactory.jsonBuilder()
                                .startObject()
                                .field("title", "Book B Title")
                                .field("num", 10)
                                .endObject()
                ).request())
                .execute().actionGet();


    }
}

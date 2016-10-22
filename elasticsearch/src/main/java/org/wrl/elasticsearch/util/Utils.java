package org.wrl.elasticsearch.util;

import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.settings.Settings;

import java.util.UUID;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * Created by wangrl on 2016/10/22.
 */
public class Utils {
    public static NodeClient getNodeClient() {
        return (NodeClient) nodeBuilder().settings(Settings.builder()
                .put("http.enabled", "false")
                .put("path.data", "target/elasticsearchTestData")
                .put("path.home", "src/test/resources/test-home-dir"))
                .clusterName(UUID.randomUUID().toString()).local(true).node()
                .client();
    }
}

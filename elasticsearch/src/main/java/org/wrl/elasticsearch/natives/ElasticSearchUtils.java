package org.wrl.elasticsearch.natives;

import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangrl on 2016/10/22.
 */
public class ElasticSearchUtils {
    private static TransportClient nodeClient = null;

    public static TransportClient getNodeClient() {
        if (nodeClient == null) {
            /*
         * 创建客户端，所有的操作都由客户端开始，这个就好像是JDBC的Connection对象
         * 用完记得要关闭
         */
            nodeClient = TransportClient.builder().settings(getSettings())
                    .build()
                    .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("localhost", 9300) ));
        }
        return nodeClient;
    }

    private static Settings getSettings() {
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

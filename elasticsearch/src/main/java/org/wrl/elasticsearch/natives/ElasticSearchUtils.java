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
    private static TransportClient transportClient = null;

    public static TransportClient getTransportClient() {
        if (transportClient == null) {
            /*
         * 创建客户端，所有的操作都由客户端开始，这个就好像是JDBC的Connection对象
         * 用完记得要关闭
         */
            transportClient = TransportClient.builder().settings(getSettings())
                    .build()
                    .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("localhost", 9300) ));
        }
        return transportClient;
    }

    private static Settings getSettings() {
        Map<String, Object> settingsMap = new HashMap<>();
        //指定集群名称
        settingsMap.put("cluster.name", "elasticsearch");
        //探测集群中机器状态
        settingsMap.put("client.transport.sniff", "true");
        //指定了ping命令响应的超时时间（默认值5s）。如果客户端和集群间的网络延迟较大或连接不稳定，可能需要调大这个值。
        settingsMap.put("client.transport.ping_timeout", "5");
        //指定检查节点可用性的时间间隔（默认值5s），与前一个参数类型。
        settingsMap.put("client.transport.nodes_sampler_interval", "5");

        //5个主分片
        settingsMap.put("number_of_shards", "5");
        //测试环境，减少副本提高速度
        settingsMap.put("number_of_replicas", "0");

        Settings settings = Settings.settingsBuilder().put(settingsMap).build();

        return settings;
    }
}

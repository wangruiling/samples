package org.wrl.elasticsearch.natives.connect;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.junit.Test;
import org.wrl.elasticsearch.natives.AbstractApi;

import java.util.HashMap;
import java.util.Map;


public class ClientTest extends AbstractApi {

	/**
	 * Exception in thread "main" java.lang.IllegalStateException: path.home is not configured
	 * 解决方法：settingsMap.put("path.home", "D:/tools/elasticsearch");
	 */
	@Test
	public void connect() {
		
		//For testing purposes we will create a test node so we can connect to
		createLocalCluster("escluster2");
		
		//We want to connect to cluster named "escluster2". Remember to set this name
		//in the ElasticSearch instance, change name set below or omit clusterName() method call.
		
		//Setting up the elasticSearch node...
		Node node = nodeBuilder()
				.clusterName("escluster2")
				.settings(getSettings())
				.client(true).data(false).node();
		
		//... end create the client
		Client client = node.client();
		
		//Now we can something with elasticSearch
		//...
		
		//after using we should close resource. 
		//In real life make sure do this in finally block.
		client.close();
		node.close();
	}

	private static Settings getSettings() {
		Map<String, Object> settingsMap = new HashMap<>();
		//指定集群名称
		settingsMap.put("cluster.name", "escluster2");
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

//		嵌入式节点可以不用打开http端口
		settingsMap.put("http.enabled", "false");

		settingsMap.put("path.home", "D:/tools/elasticsearch");
		Settings settings = Settings.settingsBuilder().put(settingsMap).build();

		return settings;
	}
}

package org.wrl.elasticsearch.natives.admin.cluster;

import org.elasticsearch.action.admin.cluster.node.info.NodeInfo;
import org.elasticsearch.action.admin.cluster.node.info.NodesInfoResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.plugins.PluginInfo;
import org.junit.Test;
import org.wrl.elasticsearch.natives.AbstractApi;


public class NodesInfoTest extends AbstractApi {

	@Test
	public void test() {
		
		Client client = getClient();

		NodesInfoResponse response = client.admin().cluster()
			.prepareNodesInfo()
//			.setNetwork(true)
//			.setPlugin(true)
			.execute().actionGet();
		
		for( NodeInfo node : response.getNodes()) {
//			System.out.println("Node eth address: " + node.getNetwork().primaryInterface().getMacAddress());
			System.out.println("Plugins: ");
//			for( PluginInfo plugin : node.getPlugins().getInfos()) {
//				System.out.println(plugin.getName() + " " + plugin.getUrl());
//			}
		}
		

	}

}

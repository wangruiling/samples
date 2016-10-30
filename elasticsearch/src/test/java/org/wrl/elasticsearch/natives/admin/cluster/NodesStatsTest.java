package org.wrl.elasticsearch.natives.admin.cluster;

import org.elasticsearch.action.admin.cluster.node.stats.NodeStats;
import org.elasticsearch.action.admin.cluster.node.stats.NodesStatsResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;
import org.wrl.elasticsearch.natives.AbstractApi;


public class NodesStatsTest extends AbstractApi {

	@Test
	public void test() {
		Client client = getClient();
		NodesStatsResponse response = client.admin().cluster()
				 .prepareNodesStats()
				 	.all()
				 .execute().actionGet();


		for( NodeStats node : response.getNodes()) {
			System.out.println("Doc count: " + node.getIndices().getDocs().getCount());
		}
	
	}

}

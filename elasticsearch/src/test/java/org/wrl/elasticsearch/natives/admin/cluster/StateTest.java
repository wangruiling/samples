package org.wrl.elasticsearch.natives.admin.cluster;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map.Entry;

import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.routing.IndexRoutingTable;
import org.junit.Test;
import org.wrl.elasticsearch.natives.AbstractApi;


public class StateTest extends AbstractApi {

	@Test
	public void test() {
		Client client = getClient();
		ClusterStateResponse response = client.admin().cluster()
			.prepareState()
			.execute().actionGet();
		
		String rn = response.getState().getRoutingNodes().prettyPrint();
		System.out.println(rn);
		
		for (Entry<String, IndexRoutingTable> entry : response.getState().getRoutingTable().indicesRouting().entrySet()) {
			System.out.println("Index: " + entry.getKey());
			System.out.println(entry.getValue().prettyPrint());
		}
	
	}

}

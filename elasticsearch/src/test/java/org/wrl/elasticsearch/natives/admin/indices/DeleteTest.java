package org.wrl.elasticsearch.natives.admin.indices;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;
import org.wrl.elasticsearch.natives.AbstractApi;


public class DeleteTest extends AbstractApi {

	@Test
	public void test() throws IOException {
		recreateIndex("news");
		
		Client client = getClient();
		DeleteIndexResponse response = client.admin().indices()
			.prepareDelete("news")
			.execute().actionGet();
	
		System.out.println("Ack: " + response.isAcknowledged());

	}

}

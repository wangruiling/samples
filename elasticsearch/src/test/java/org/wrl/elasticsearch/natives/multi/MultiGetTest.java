package org.wrl.elasticsearch.natives.multi;

import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;
import org.wrl.elasticsearch.natives.AbstractApi;


public class MultiGetTest extends AbstractApi {

	@Test
	public void test() {
		Client client = getClient();
		
		MultiGetResponse response = client.prepareMultiGet()
				.add("library", "book", "1", "2")
				.execute().actionGet();
		
		for(MultiGetItemResponse resp: response.getResponses()) {
			System.out.println(resp.getId());
//			resp.getResponse();
		}
	}

}

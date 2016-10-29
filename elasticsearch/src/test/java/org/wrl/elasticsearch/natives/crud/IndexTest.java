package org.wrl.elasticsearch.natives.crud;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;
import org.wrl.elasticsearch.natives.AbstractApi;


public class IndexTest extends AbstractApi {

	@Test
	public void test() {
		Client client = getClient();
		
		IndexResponse response = client.prepareIndex("library", "book", "2")
				.setSource("{ \"title\": \"Mastering ElasticSearch\"}")
				.execute().actionGet();
		
		System.out.println("Document: " + response.getIndex() 
				+ "/" + response.getType() 
				+ "/" + response.getId());
		
	}

}

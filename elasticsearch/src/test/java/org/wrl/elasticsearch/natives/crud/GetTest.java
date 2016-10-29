package org.wrl.elasticsearch.natives.crud;

import java.io.IOException;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.get.GetField;
import org.junit.Test;
import org.wrl.elasticsearch.natives.AbstractApi;


public class GetTest extends AbstractApi {

	@Test
	public void test() throws IOException {
		recreateIndex("library");
		createSomeBooks("library");
		
		Client client = getClient();
		
		GetResponse response = client.prepareGet("library", "book", "1")
				.setFields("title", "_source")
				.execute().actionGet();		
		
		if (response.isExists()) {
			GetField field = response.getField("title");
		
			System.out.println("Document: " + response.getIndex() 
				+ "/" + response.getType() 
				+ "/" + response.getId());
			System.out.println("Title: " + field.getValue());
			System.out.println("Source: " + response.getSourceAsString());
		} else {
			System.out.println("Document not found.");
		}
	}

}

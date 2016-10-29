package org.wrl.elasticsearch.natives.crud;

import java.io.IOException;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.get.GetField;
import org.junit.Test;
import org.wrl.elasticsearch.natives.AbstractApi;


public class GetAsynchTest extends AbstractApi {

	@Test
	public void test() throws InterruptedException, IOException {
		recreateIndex("library");
		createSomeBooks("library");
		
		Client client = getClient();
		
		//Execute aynchronously the fetch request
		ListenableActionFuture<GetResponse> future = client
				.prepareGet("library", "book", "1")
				.setFields("title", "_source")
				.execute();
		
		//Setting up what to do when request returned
		future.addListener(new ActionListener<GetResponse>() {
			
			@Override
			public void onResponse(GetResponse response) {
				GetField field = response.getField("title");
				
				System.out.println("Document: " + response.getIndex() 
						+ "/" + response.getType() 
						+ "/" + response.getId());
				System.out.println("Title: " + field.getValue());
				System.out.println("Source: " + response.getSourceAsString());
			}
			
			@Override
			public void onFailure(Throwable e) {
				throw new RuntimeException(e);
			}
		});
		//The program goes here independently from the request
		System.out.println("AfterCall.");
		System.out.println("" + future.isCancelled() + future.isDone());

		//Some delay to make sure that program doesn't end before call to ElasticSearch is finished
		Thread.sleep(10000);
	}

}

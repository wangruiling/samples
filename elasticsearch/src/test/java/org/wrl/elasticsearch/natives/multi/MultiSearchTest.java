package org.wrl.elasticsearch.natives.multi;

import java.io.IOException;

import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.MultiSearchResponse.Item;
import org.elasticsearch.client.Client;
import org.junit.Test;
import org.wrl.elasticsearch.natives.AbstractApi;


public class MultiSearchTest extends AbstractApi {

	@Test
	public void test() throws IOException {
		recreateIndex("library");
		createSomeBooks("library");
		recreateIndex("news");
		Client client = getClient();
		
		MultiSearchResponse response = client.prepareMultiSearch()
				.add(client.prepareSearch("library", "book").request())
				.add(client.prepareSearch("news")
//						.setFilter(FilterBuilders.termFilter("tags", "important"))
				)
				.execute().actionGet();
		
		for(Item resp: response.getResponses()) {
			System.out.println(resp.getResponse().getTookInMillis());
		}
	}

}

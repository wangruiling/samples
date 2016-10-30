package org.wrl.elasticsearch.natives.search;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.wrl.elasticsearch.natives.AbstractApi;


public class CountTest extends AbstractApi {

	@Test
	public void test() {
		recreateIndex("library");
		
		Client client = getClient();

		SearchResponse response = client.prepareSearch("library")
				.setQuery(QueryBuilders.termQuery("title", "elastic"))
				.setSize(0)
				.execute().actionGet();
		
		System.out.println(response.getHits().getTotalHits());
	}

}

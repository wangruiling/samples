package org.wrl.elasticsearch.natives.search;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.wrl.elasticsearch.natives.AbstractApi;

import java.io.IOException;


public class SearchTest extends AbstractApi {

	@Test
	public void testCount() throws IOException {
		recreateIndex("library");
		createSomeBooks("library");
		Client client = getClient();

		QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();

		System.out.println("Generated query: " + queryBuilder.toString());

		SearchResponse response = client.prepareSearch("library")
				.setQuery(queryBuilder)
				.addFields("title", "_source")
				.setSize(0)
				.execute().actionGet();
		showHits(response);

	}

	@Test
	public void test() {
		recreateIndex("library");
		
		Client client = getClient();
		
		QueryBuilder queryBuilder = QueryBuilders
				.disMaxQuery()
					.add(QueryBuilders.termQuery("title", "Elastic"))
					.add(QueryBuilders.prefixQuery("title", "el"));
		
		System.out.println("Generated query: " + queryBuilder.toString());
		
		SearchResponse response = client.prepareSearch("library")
				.setQuery(queryBuilder)
				.addFields("title", "_source")
				.execute().actionGet();
		showHits(response);
		System.out.println(response.getHits().getTotalHits());
	}
	
	@Test
	public void paging() {
		recreateIndex("library");
		
		Client client = getClient();
		
		SearchResponse response = client.prepareSearch("library")
				.setQuery(QueryBuilders.matchAllQuery())
				.setFrom(10)
				.setSize(20)
				.execute().actionGet();
		showHits(response);
	}
	
	@Test
	public void sorting() {
		recreateIndex("library");
		
		Client client = getClient();
		
		SearchRequestBuilder b = client.prepareSearch("library")
		.setQuery(QueryBuilders.matchAllQuery())
		.addSort(SortBuilders.fieldSort("title"))
		.addSort("_score", SortOrder.DESC);
		
		System.err.println("Generated query: " + b.toString());
		
		SearchResponse response = client.prepareSearch("library")
				.setQuery(QueryBuilders.matchAllQuery())
				.addSort(SortBuilders.fieldSort("title"))
				.addSort("_score", SortOrder.DESC)
				.execute().actionGet();
		showHits(response);
		
	}
	
	private void showHits(final SearchResponse response) {
		for(SearchHit hit: response.getHits().getHits()) {
			System.out.println("Id: " + hit.getId());
			System.out.println("Score: " + hit.getScore());
			if (hit.getFields().containsKey("title")) {
				System.out.println("field.title: " + hit.getFields().get("title").getValue());
			}

			System.out.println("source.title: " + hit.getSource().get("title"));
			System.out.println();
		}	
	}

}

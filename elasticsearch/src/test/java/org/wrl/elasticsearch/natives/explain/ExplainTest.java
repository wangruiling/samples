package org.wrl.elasticsearch.natives.explain;

import java.io.IOException;

import org.elasticsearch.action.explain.ExplainResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.wrl.elasticsearch.natives.AbstractApi;


public class ExplainTest extends AbstractApi {

	@Test
	public void test() throws IOException {
		recreateIndex("library");
		createSomeBooks("library");
		
		Client client = getClient();

		
		ExplainResponse response = client.prepareExplain("library", "book", "1")
				.setQuery(QueryBuilders.termQuery("title", "elastic"))
				.execute().actionGet();
		showHits(response);

	}
	
	private void showHits(final ExplainResponse response) {
		if (response.isExists()) {
			System.out.println(response.getExplanation().getDescription());
		}
	}

}

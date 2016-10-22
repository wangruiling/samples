package org.wrl.elasticsearch.natives;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.RangeBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wangrl on 2016/10/22.
 */
public class ElasticSearchUtilsTest {

    /**
     * {
     "query" : {
     "match_all" : {}
     },
     "filter" : {
     "term" : { "category" : "book" }
     },
     "facets" : {
     "price" : {
     "range" : {
     "field" : "price",
     "ranges" : [
     { "to" : 30 },
     { "from" : 30 }
     ]
     }
     }
     }
     }
     */
    @Test
    public void query_with_filter() {
        TransportClient nodeClient = ElasticSearchUtils.getNodeClient();

        //查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
//        FilteredQueryBuilder filteredQueryBuilder = QueryBuilders.filteredQuery(matchAllQueryBuilder, QueryBuilders.termQuery("category", "book"));
        QueryBuilder filterQueryBuilder = QueryBuilders.termQuery("category", "book");

        boolQueryBuilder.must(matchAllQueryBuilder);
        boolQueryBuilder.filter(filterQueryBuilder);

        //切面
        RangeBuilder aggregationBuilder = AggregationBuilders.range("priceAggretion");
        aggregationBuilder.field("price");
        aggregationBuilder.addUnboundedFrom(30);
        aggregationBuilder.addUnboundedTo(30);

        FilterAggregationBuilder filterAggregationBuilder = AggregationBuilders.filter("aggregaFilter");
        filterAggregationBuilder.filter(QueryBuilders.termQuery("title", "2"));

        SearchRequestBuilder sbuilder = nodeClient.prepareSearch("books") //index name
                .setTypes("book") //type name
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder)                // Query
//                .setPostFilter(filterBuilder)
                .addAggregation(aggregationBuilder)    // Aggregation
                .addAggregation(filterAggregationBuilder)
//                .setExplain(false)
                .setFrom(0).setSize(10);
        System.out.println("query:");
        System.out.println(sbuilder.toString());
        SearchResponse response = sbuilder.execute().actionGet();
        System.out.println("result:");
        System.out.println(response.toString());
    }
}
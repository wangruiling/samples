package org.wrl.samples.es.common;

import com.bluejean.store.web.es.aggregation.AggregationResult;
import com.bluejean.store.web.es.aggregation.result.Bucket;
import com.bluejean.store.web.es.aggregation.result.BucketResult;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.InternalTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wangrl
 * @Date: 2016-04-29 14:42
 */
public class DefaultAggregationMapper {
    public static AggregationResult parse(Aggregation aggregation) {
        if (aggregation instanceof InternalTerms) {
            return parseTerm((InternalTerms) aggregation);
        }

        return null;
    }

    private static AggregationResult parseTerm(InternalTerms terms) {
        List<Bucket> entries = new ArrayList<>(terms.getBuckets().size());
        List<Terms.Bucket> bucketList = terms.getBuckets();

        for (Terms.Bucket bucket : bucketList) {
            entries.add(new Bucket(bucket.getDocCount(), bucket.getDocCountError(), bucket.getKey(), bucket.getKeyAsString(), getBucketKeyAsNumber(bucket)));
        }
        return new BucketResult(terms.getName(), entries, terms.getSumOfOtherDocCounts(), terms.getDocCountError(), terms.getSumOfOtherDocCounts());
    }

    private static Number getBucketKeyAsNumber(Terms.Bucket bucket) {
        Number number = null;
        try {
            bucket.getKeyAsString();
        } catch (Exception ex) {

        }
        return number;
    }
}

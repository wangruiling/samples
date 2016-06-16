package com.bluejean.store.web.es.aggregation.result;

import com.bluejean.store.web.es.aggregation.AbstractAggregationResult;
import com.bluejean.store.web.es.aggregation.AggregationType;

import java.util.List;

/**
 * @author: wangrl
 * @Date: 2016-04-29 15:24
 */
public class BucketResult extends AbstractAggregationResult{
    List<Bucket> buckets;
    private long total;
    private long docCountError;
    private long sumOfOtherDocCounts;

    public BucketResult(String name, List<Bucket> buckets, long total, long docCountError, long sumOfOtherDocCounts) {
        super(name, AggregationType.terms);
        this.buckets = buckets;
        this.total = total;
        this.docCountError = docCountError;
        this.sumOfOtherDocCounts = sumOfOtherDocCounts;
    }

    public List<Bucket> getBuckets() {
        return buckets;
    }

    public void setBuckets(List<Bucket> buckets) {
        this.buckets = buckets;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getDocCountError() {
        return docCountError;
    }

    public void setDocCountError(long docCountError) {
        this.docCountError = docCountError;
    }

    public long getSumOfOtherDocCounts() {
        return sumOfOtherDocCounts;
    }

    public void setSumOfOtherDocCounts(long sumOfOtherDocCounts) {
        this.sumOfOtherDocCounts = sumOfOtherDocCounts;
    }
}

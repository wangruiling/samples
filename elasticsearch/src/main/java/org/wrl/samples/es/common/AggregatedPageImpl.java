package org.wrl.samples.es.common;

import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 包含聚合数据的分页数据
 * @author: wangrl
 * @Date: 2016-04-25 15:54
 */
public class AggregatedPageImpl <T> extends PageImpl<T> implements AggregatedPage<T>{
    private List<Terms> facets;
    //private Map<String, Terms> mapOfAggregations = new HashMap<>();

    public AggregatedPageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public AggregatedPageImpl(List<T> content) {
        super(content);
    }

    public AggregatedPageImpl(List<T> content, Pageable pageable, long total, List<Terms> termses) {
        super(content, pageable, total);
        this.facets = termses;
        //for (Terms terms : termses) {
        //    mapOfAggregations.put(terms.getName(), terms);
        //}
    }

    public List<Terms> getFacets() {
        return facets;
    }

    public void setFacets(List<Terms> facets) {
        this.facets = facets;
    }

    @Override
    public boolean hasAggregations() {
        return CollectionUtils.isNotEmpty(facets);
    }

    @Override
    public List<Terms> getAggregations() {
        return facets;
    }


}

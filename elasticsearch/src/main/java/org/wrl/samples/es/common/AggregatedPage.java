package org.wrl.samples.es.common;

import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 包含聚合信息的分页数据接口
 * Created by Administrator on 2016/4/25.
 */
public interface AggregatedPage <T> extends Page<T> {
    boolean hasAggregations();

    List<Terms> getAggregations();

    //Terms getAggregation(String name);
}

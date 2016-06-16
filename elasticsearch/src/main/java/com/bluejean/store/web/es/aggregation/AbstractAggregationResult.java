package com.bluejean.store.web.es.aggregation;

import org.springframework.util.Assert;

/**
 * @author: wangrl
 * @Date: 2016-04-29 15:14
 */
public class AbstractAggregationResult implements AggregationResult {
    private final String name;
    private final AggregationType type;

    protected AbstractAggregationResult(String name, AggregationType type) {
        Assert.hasText(name, "Facet name can't be null and should have a value");
        this.name = name;
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AggregationType getType() {
        return type;
    }
}

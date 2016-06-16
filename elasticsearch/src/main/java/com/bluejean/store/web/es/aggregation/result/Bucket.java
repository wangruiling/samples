package com.bluejean.store.web.es.aggregation.result;

/**
 * @author: wangrl
 * @Date: 2016-04-29 15:24
 */
public class Bucket {
    private long docCount;
    private long docCountError;
    private Object key;
    private String keyAsString;
    private Number keyAsNumber;

    public Bucket(long docCount, long docCountError, Object key, String keyAsString, Number keyAsNumber) {
        this.docCount = docCount;
        this.docCountError = docCountError;
        this.key = key;
        this.keyAsString = keyAsString;
        this.keyAsNumber = keyAsNumber;
    }

    public long getDocCount() {
        return docCount;
    }

    public void setDocCount(long docCount) {
        this.docCount = docCount;
    }

    public long getDocCountError() {
        return docCountError;
    }

    public void setDocCountError(long docCountError) {
        this.docCountError = docCountError;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public String getKeyAsString() {
        return keyAsString;
    }

    public void setKeyAsString(String keyAsString) {
        this.keyAsString = keyAsString;
    }

    public Number getKeyAsNumber() {
        return keyAsNumber;
    }

    public void setKeyAsNumber(Number keyAsNumber) {
        this.keyAsNumber = keyAsNumber;
    }
}

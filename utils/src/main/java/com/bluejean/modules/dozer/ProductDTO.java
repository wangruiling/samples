package com.bluejean.modules.dozer;

import org.dozer.Mapping;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-12-10 17:56
 */
public class ProductDTO {
    @Mapping("id")
    private long productId;
    @Mapping("name")
    private String productName;
    @Mapping("description")
    private String desc;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

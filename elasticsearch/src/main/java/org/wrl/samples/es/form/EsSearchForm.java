package org.wrl.samples.es.form;


import com.google.common.collect.Maps;

import java.io.Serializable;
import java.time.temporal.Temporal;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2014-08-18 16:32
 */
public class EsSearchForm implements Serializable {
    /************* 分页相关 *****************/
    //当前页，从0开始
    private Integer currentPage;
    //每页多少行默认30
    private Integer pageSize;

    /************** 2015/4/22 最新上线图片 By:wangrl start ****************/
    //是否最新上线搜索{0:非、1：是)
    private Integer latestOnline = 0;
    /************** 2015/4/22 最新上线图片 By:wangrl end    **************/

    /************* 基本查询相关 *****************/
    //关键词
    private String keywords;

    /************* 高级搜索 *****************/
    //品牌id
    private String brandId;
    //品牌名字
    private String brandName;
    //摄像师id
    private String holderId;
    //摄像师名字
    private String photographer;
    //是否套图
    private Integer isSet;
//    许可类型Id
//    素材类型(图片、插画、矢量图、CGI)
    private String attributeSetId;
    /*//素材类型(图片、插画、矢量图、CGI)
    private String typeId;*/
    //RF,RM
    private String saleFormat = "RF,RM";

    //此值为关键词字段属性
    private String q1;
    private String q2;
    private String q3;
    //排除关键字
    private String ex_q;
    //相似图片id
    private Integer mltpid;
    //相似图片编号
    private String mltpn;
    //高级搜索-编号搜索，支持空格分割
    private String pn;
    /************** 排序相关 2014/9/10 By:wangrl start ****************/
    //排序字段
    private String orderKey;
    //排序顺序
    private String orderType;
    /************** 排序相关 2014/9/10 By:wangrl end    **************/

    /************** 后台使用 2014/9/10 By:wangrl start ****************/
    //过滤
    private Map<String, Set<Object>> filterMap = Maps.newHashMap();
    //统计信息(其中：key为要统计的字段, value为该字段中需要统计的值)
    private Map<String, Set<String>> aggreations = Maps.newHashMap();
    //最近上线时间最小值
    private Temporal onlineTimeFrom;
    //最近上线时间最大值
    private Temporal onlineTimeTo;

    /************* 后台使用 2014/9/10 By:wangrl end    **************/

    private Integer productLevel;

    public String getQ1() {
        return q1;
    }

    public void setQ1(String q1) {
        this.q1 = q1;
    }

    public String getQ2() {
        return q2;
    }

    public void setQ2(String q2) {
        this.q2 = q2;
    }

    public String getMltpn() {
        return mltpn;
    }

    public void setMltpn(String mltpn) {
        this.mltpn = mltpn;
    }

    public String getQ3() {
        return q3;
    }

    public void setQ3(String q3) {
        this.q3 = q3;
    }

    public Integer getMltpid() {
        return mltpid;
    }

    public void setMltpid(Integer mltpid) {
        this.mltpid = mltpid;
    }

    public Integer getIsSet() {
        return isSet;
    }

    public void setIsSet(Integer isSet) {
        this.isSet = isSet;
    }

    public Map<String, Set<String>> getAggreations() {
        return aggreations;
    }

    public void setAggreations(Map<String, Set<String>> aggreations) {
        this.aggreations = aggreations;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getAttributeSetId() {
        return attributeSetId;
    }

    public void setAttributeSetId(String attributeSetId) {
        this.attributeSetId = attributeSetId;
    }

    public String getSaleFormat() {
        return saleFormat;
    }

    public void setSaleFormat(String saleFormat) {
        this.saleFormat = saleFormat;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getEx_q() {
        return ex_q;
    }

    public void setEx_q(String ex_q) {
        this.ex_q = ex_q;
    }

    public Map<String, Set<Object>> getFilterMap() {
        return filterMap;
    }

    public void setFilterMap(Map<String, Set<Object>> filterMap) {
        this.filterMap = filterMap;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getHolderId() {
        return holderId;
    }

    public void setHolderId(String holderId) {
        this.holderId = holderId;
    }

    public Integer getLatestOnline() {
        return latestOnline;
    }

    public void setLatestOnline(Integer latestOnline) {
        this.latestOnline = latestOnline;
    }

    public Temporal getOnlineTimeFrom() {
        return onlineTimeFrom;
    }

    public void setOnlineTimeFrom(Temporal onlineTimeFrom) {
        this.onlineTimeFrom = onlineTimeFrom;
    }

    public Temporal getOnlineTimeTo() {
        return onlineTimeTo;
    }

    public void setOnlineTimeTo(Temporal onlineTimeTo) {
        this.onlineTimeTo = onlineTimeTo;
    }

    public Integer getProductLevel() {
        return productLevel;
    }

    public void setProductLevel(Integer productLevel) {
        this.productLevel = productLevel;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }
}

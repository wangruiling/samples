package org.wrl.samples.es.esentity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * 索引中的商品实体
 * @author: wangrl
 * @Date: 2014-08-15 14:04
 */
@Document(indexName = "product_idx", type = "product_idx_type", createIndex = false)
//@Setting(settingPath = "/settings/test-settings.json")
public class EsProductEntity implements Serializable {
    @Id
    private Integer productId;
    private Integer productLevel;
    @Field(type = FieldType.Double)
    private Double sortIdInBrand;
    //是否套图
    private Integer isSet;
//    private String hitCount;
    @Field(type = FieldType.Double)
    private Double sortId;
    @Field(type = FieldType.Double)
    private Double sortInBrandProductLevel;
    @Field(type = FieldType.Double)
    private Double sortInProductLevel;
    private Integer status;
//    @JsonIgnore
    //主关键词
    @Field(type = FieldType.String, store = false, analyzer = "ik_smart", searchAnalyzer = "ik_smart_syno")
    private String cnKeywords;
//    @JsonIgnore
    @Field(type = FieldType.String, store = false, analyzer = "mmseg", searchAnalyzer = "mmseg")
    private String enKeywords;
//    素材类型(图片、插画、矢量图、CGI)
    private Integer typeId;
    //品牌
    private Integer brandId;
    private String brandNameCn;
    private String brandNameEn;
    private String brandCountry;
    private Integer brandSortId;

    //    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private Date createTime;
    //    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private Date updateTime;
    //    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private Date onlineTime;
    private String isLock;
    private String isCoupon;
    private String isTop;
    private String isHot;
    private String isSys;

    private String orientation;
    //许可类型
    private String saleFormat;
    //许可类型id
    private Integer attributeSetId;
//    private Integer pbasSortId;
    //商品编号
    private String productNumber;
    //图片系列
    private String shortNumber;
    private String giImageNumber;
    private String gicImageNumber;
    private String corbisImageNumber;
    //原始编号
    private String originalNo;

    //中文标题
    private String chineseCaption;
    //英文标题
    private String englishCaption;

    //摄影师
    private Integer holderId;
    private String realName;
    private String photographer;

    //600点无水印图片信息
    private Integer pmdWidth;
    private Integer pmdHeight;
    private String pmdMediaUrl;

    //600点中文水印图片信息
    private Integer pmcWidth;
    private Integer pmcHeight;
    private String pmcMediaUrl;

    //600点英文水印图片信息
    private Integer pmeWidth;
    private Integer pmeHeight;
    private String pmeMediaUrl;

    //200点缩略图图片信息
    private Integer pmtWidth;
    private Integer pmtHeight;
    private String pmtMediaUrl;

    //elasticsearch搜索评分
//    private float _score;



    public Integer getIsSet() {
        return isSet;
    }

    public void setIsSet(Integer isSet) {
        this.isSet = isSet;
    }

    public Integer getBrandSortId() {
        return brandSortId;
    }

    public void setBrandSortId(Integer brandSortId) {
        this.brandSortId = brandSortId;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS+08:00", timezone = "GMT+08:00")
//    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS+08:00", timezone = "GMT+08:00")
//    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }

    public String getIsCoupon() {
        return isCoupon;
    }

    public void setIsCoupon(String isCoupon) {
        this.isCoupon = isCoupon;
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    public String getIsHot() {
        return isHot;
    }

    public void setIsHot(String isHot) {
        this.isHot = isHot;
    }

    public String getIsSys() {
        return isSys;
    }

    public void setIsSys(String isSys) {
        this.isSys = isSys;
    }

    public Double getSortId() {
        return sortId;
    }

    public void setSortId(Double sortId) {
        this.sortId = sortId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCnKeywords() {
        return cnKeywords;
    }

    public void setCnKeywords(String cnKeywords) {
        this.cnKeywords = cnKeywords;
    }

    public String getEnKeywords() {
        return enKeywords;
    }

    public void setEnKeywords(String enKeywords) {
        this.enKeywords = enKeywords;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getBrandNameCn() {
        return brandNameCn;
    }

    public void setBrandNameCn(String brandNameCn) {
        this.brandNameCn = brandNameCn;
    }

    public String getBrandNameEn() {
        return brandNameEn;
    }

    public void setBrandNameEn(String brandNameEn) {
        this.brandNameEn = brandNameEn;
    }

    public String getBrandCountry() {
        return brandCountry;
    }

    public void setBrandCountry(String brandCountry) {
        this.brandCountry = brandCountry;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public Integer getAttributeSetId() {
        return attributeSetId;
    }

    public void setAttributeSetId(Integer attributeSetId) {
        this.attributeSetId = attributeSetId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getSaleFormat() {
        return saleFormat;
    }

    public void setSaleFormat(String saleFormat) {
        this.saleFormat = saleFormat;
    }

    public String getChineseCaption() {
        return chineseCaption;
    }

    public void setChineseCaption(String chineseCaption) {
        this.chineseCaption = chineseCaption;
    }

    public String getEnglishCaption() {
        return englishCaption;
    }

    public void setEnglishCaption(String englishCaption) {
        this.englishCaption = englishCaption;
    }

    public Integer getPmcWidth() {
        return pmcWidth;
    }

    public void setPmcWidth(Integer pmcWidth) {
        this.pmcWidth = pmcWidth;
    }

    public Integer getPmcHeight() {
        return pmcHeight;
    }

    public void setPmcHeight(Integer pmcHeight) {
        this.pmcHeight = pmcHeight;
    }

    public String getPmcMediaUrl() {
        return pmcMediaUrl;
    }

    public void setPmcMediaUrl(String pmcMediaUrl) {
        this.pmcMediaUrl = pmcMediaUrl;
    }

    public Integer getPmeWidth() {
        return pmeWidth;
    }

    public void setPmeWidth(Integer pmeWidth) {
        this.pmeWidth = pmeWidth;
    }

    public Integer getPmeHeight() {
        return pmeHeight;
    }

    public void setPmeHeight(Integer pmeHeight) {
        this.pmeHeight = pmeHeight;
    }

    public String getPmeMediaUrl() {
        return pmeMediaUrl;
    }

    public void setPmeMediaUrl(String pmeMediaUrl) {
        this.pmeMediaUrl = pmeMediaUrl;
    }

    public Integer getPmtWidth() {
        return pmtWidth;
    }

    public void setPmtWidth(Integer pmtWidth) {
        this.pmtWidth = pmtWidth;
    }

    public Integer getPmtHeight() {
        return pmtHeight;
    }

    public void setPmtHeight(Integer pmtHeight) {
        this.pmtHeight = pmtHeight;
    }

    public String getPmtMediaUrl() {
        return pmtMediaUrl;
    }

    public void setPmtMediaUrl(String pmtMediaUrl) {
        this.pmtMediaUrl = pmtMediaUrl;
    }

    public String getShortNumber() {
        return shortNumber;
    }

    public void setShortNumber(String shortNumber) {
        this.shortNumber = shortNumber;
    }

    public Integer getHolderId() {
        return holderId;
    }

    public void setHolderId(Integer holderId) {
        this.holderId = holderId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGiImageNumber() {
        return giImageNumber;
    }

    public void setGiImageNumber(String giImageNumber) {
        this.giImageNumber = giImageNumber;
    }

    public String getGicImageNumber() {
        return gicImageNumber;
    }

    public void setGicImageNumber(String gicImageNumber) {
        this.gicImageNumber = gicImageNumber;
    }

    public String getCorbisImageNumber() {
        return corbisImageNumber;
    }

    public void setCorbisImageNumber(String corbisImageNumber) {
        this.corbisImageNumber = corbisImageNumber;
    }

    public String getOriginalNo() {
        return originalNo;
    }

    public void setOriginalNo(String originalNo) {
        this.originalNo = originalNo;
    }

//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS+08:00", timezone = "GMT+08:00")
//    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Integer getPmdWidth() {
        return pmdWidth;
    }

    public void setPmdWidth(Integer pmdWidth) {
        this.pmdWidth = pmdWidth;
    }

    public Integer getPmdHeight() {
        return pmdHeight;
    }

    public void setPmdHeight(Integer pmdHeight) {
        this.pmdHeight = pmdHeight;
    }

    public String getPmdMediaUrl() {
        return pmdMediaUrl;
    }

    public void setPmdMediaUrl(String pmdMediaUrl) {
        this.pmdMediaUrl = pmdMediaUrl;
    }

    public Integer getProductLevel() {
        return productLevel;
    }

    public void setProductLevel(Integer productLevel) {
        this.productLevel = productLevel;
    }

    public Double getSortIdInBrand() {
        return sortIdInBrand;
    }

    public void setSortIdInBrand(Double sortIdInBrand) {
        this.sortIdInBrand = sortIdInBrand;
    }

    public Double getSortInBrandProductLevel() {
        return sortInBrandProductLevel;
    }

    public void setSortInBrandProductLevel(Double sortInBrandProductLevel) {
        this.sortInBrandProductLevel = sortInBrandProductLevel;
    }

    public Double getSortInProductLevel() {
        return sortInProductLevel;
    }

    public void setSortInProductLevel(Double sortInProductLevel) {
        this.sortInProductLevel = sortInProductLevel;
    }
}

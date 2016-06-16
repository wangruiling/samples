package org.wrl.samples.es.esservice.impl;


import com.bluejean.store.web.es.aggregation.AggregationResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.wrl.samples.es.esentity.EsProductEntity;
import org.wrl.samples.es.esrepository.EsProductRepository;
import org.wrl.samples.es.esservice.IEsSearchService;
import org.wrl.samples.es.form.EsSearchForm;
import org.wrl.samples.es.form.EsSearchResult;

import javax.annotation.Resource;
import java.lang.invoke.MethodHandles;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2014-08-18 16:43
 */
@Service("esSearchService")
public class EsSearchServiceImpl implements IEsSearchService {
    protected static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    //1毫秒
    private static final int ONE_MILLISECOND = 1000 * 1000;
    //999毫秒
    private static final int MAX_MILLISECOND = ONE_MILLISECOND * 999;
    @Resource(name = "esProductRepository")
    private EsProductRepository esProductRepository;

    @Override
    public List<EsProductEntity> findByProductIds(List<Integer> productIds) {
        if (productIds != null && productIds.size() > 0) {
            return (List<EsProductEntity>) esProductRepository.findAll(productIds);
        } else {
            return Lists.newArrayList();
        }
    }

    @Override
    public List<AggregationResult> aggregatesForSearch(EsSearchForm queryForm) {
        if (queryForm == null) {
            return null;
        }
        convertFilter(queryForm);
        convertAggreations(queryForm);
        return esProductRepository.aggregateForSearch(queryForm);
    }

    /**
     * 最新上线图片数量
     * @return
     */
    @Override
    public long findLatestOnlineNumber() {
        EsSearchForm queryForm = new EsSearchForm();
        //putOnlineTimeRange(queryForm);
        return esProductRepository.countByQueryForm(queryForm);
    }

    @Override
    public EsSearchResult baseSearch(EsSearchForm queryForm) {
        EsSearchResult searchResult = null;
        if (queryForm == null) {
            return null;
        }

        PageRequest pageRequest = initSortCondition(queryForm);

        convertFilter(queryForm);
        convertAggreations(queryForm);
        if (org.apache.commons.lang3.StringUtils.isNotBlank(queryForm.getMltpn())) {
            //相似图片
            if (logger.isInfoEnabled()) {
                logger.info("mltpn:{}", queryForm.getMltpn());
            }
            EsProductEntity productEntity = esProductRepository.findByProductNumber(queryForm.getMltpn());
            if (productEntity != null) {
                searchResult = new EsSearchResult();
                searchResult.setFixedItem(productEntity);

                Page<EsProductEntity> pages = esProductRepository.queryMoreLikeByPage(pageRequest, productEntity);
                searchResult.setPages(pages);
            }
        } else {
            //普通搜索
            searchResult = esProductRepository.queryByPage(pageRequest, queryForm);

            if (searchResult == null || searchResult.getPages() == null || !searchResult.getPages().hasContent()) {
                //进行原始编号系列搜索
                esProductRepository.findByOriginalNo(pageRequest, queryForm, searchResult);
            }
        }

        return searchResult;
    }

    /**
     * 构造分页数据
     * @param queryForm
     * @return
     */
    private PageRequest initSortCondition(EsSearchForm queryForm) {
        Sort sort = null;

        if (org.apache.commons.lang3.StringUtils.isNotBlank(queryForm.getOrderKey())) {
            Sort.Order order = new Sort.Order(Sort.Direction.fromStringOrNull(queryForm.getOrderType()), queryForm.getOrderKey());
            sort = new Sort(order);
        }

        Integer currentPage = queryForm.getCurrentPage();
        Integer pageSize = 0;
        if (currentPage == null || currentPage < 0) {
            currentPage = 0;
        }

        if(queryForm.getPageSize() != null && queryForm.getPageSize() > 0 && queryForm.getPageSize() <= 200 ){
            pageSize = queryForm.getPageSize();
        } else {
            pageSize = 5;
        }
        return new PageRequest(currentPage, pageSize, sort);
    }

    /**
     * 统计信息转换
     * @param queryForm
     */
    private void convertAggreations(EsSearchForm queryForm) {
        //从redis中获取热词信息
        Set<String> hotKeywords = Sets.newHashSet("运动", "两个人", "中国人", "自行车");
        if (hotKeywords != null && hotKeywords.size() > 0) {
            queryForm.getAggreations().put("cnKeywords", hotKeywords);
        }
    }

    /**
     * 过滤条件转换
     * @param queryForm
     */
    private void convertFilter(EsSearchForm queryForm) {
        //商品状态过滤
//        List<Integer> statusSet = Lists.newArrayList(4);
//        queryForm.getFilterMap().put("status", statusSet);

        //摄影师过滤
        if (org.apache.commons.lang3.StringUtils.isNotBlank(queryForm.getHolderId())) {
            Set<Object> holderIdSet = Sets.newHashSet();
            for (String holderId : org.apache.commons.lang3.StringUtils.split(queryForm.getHolderId(), ",")) {
                if (org.apache.commons.lang3.StringUtils.isNotBlank(holderId)) {
                    holderIdSet.add(Integer.valueOf(holderId));
                }
            }
            if (holderIdSet.size() > 0) {
                queryForm.getFilterMap().put("holderId", holderIdSet);
            }
        }

        //品牌过滤
        if (org.apache.commons.lang3.StringUtils.isNotBlank(queryForm.getBrandId())) {
            String[] brandIds = org.apache.commons.lang3.StringUtils.split(queryForm.getBrandId(), ",");
            Set<Object> brandIdSet = Sets.newHashSet();
            for (String brandId : brandIds) {
                if (org.apache.commons.lang3.StringUtils.isNotBlank(brandId)) {
                    brandIdSet.add(Integer.valueOf(brandId));
                }
            }
            if (brandIdSet.size() > 0) {
                queryForm.getFilterMap().put("brandId", brandIdSet);
            }
        }

        //类型过滤RF、RM
        if (org.apache.commons.lang3.StringUtils.isNotBlank(queryForm.getSaleFormat())) {
            String[] saleFormats = org.apache.commons.lang3.StringUtils.split(queryForm.getSaleFormat(), ",");
            if (saleFormats != null && saleFormats.length == 1) {
                Set<Object> saleFormatValues = Sets.newHashSet();
                for (String saleFormat : saleFormats) {
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(saleFormat)) {
                        saleFormatValues.add(saleFormat);
                    }
                }
                if (saleFormatValues.size() > 0) {
                    queryForm.getFilterMap().put("saleFormat", saleFormatValues);
                }
            }
        }

        //素材过滤
//        素材类型(图片、插画、矢量图、CGI)
        if (org.apache.commons.lang3.StringUtils.isNotBlank(queryForm.getAttributeSetId())) {
            String[] attributeSetIds = org.apache.commons.lang3.StringUtils.split(queryForm.getAttributeSetId(), ",");

            if (attributeSetIds != null && attributeSetIds.length == 1) {
                String attributeSetId = attributeSetIds[0];
                String keyword = null;

                if (org.apache.commons.lang3.StringUtils.equals(attributeSetId, "1")) {
                    keyword = "照片";
//                    attributeSetList = Sets.newHashSet(keyword);
                } else if (org.apache.commons.lang3.StringUtils.equals(attributeSetId, "2")) {
                    keyword = "插画";
//                    attributeSetList = Sets.newHashSet(keyword);
                }

                if (org.apache.commons.lang3.StringUtils.isNotBlank(keyword)) {
                    if (queryForm.getKeywords() != null) {
                        queryForm.setKeywords(keyword + "," + queryForm.getKeywords());
                    } else {
                        queryForm.setKeywords(keyword);
                    }
                }
//                if (attributeSetList != null && attributeSetList.size() > 0) {
//                    queryForm.getFilterMap().put("cnKeywords", attributeSetList);
//                }
            }

        }

        //图片星级过滤
        if (Objects.nonNull(queryForm.getProductLevel())) {
            Set<Object> productLevelSet = new HashSet<>(1);
            productLevelSet.add(queryForm.getProductLevel());
            queryForm.getFilterMap().put("productLevel", productLevelSet);
        }

        //是否套图
        /*if (queryForm.getIsSet() != null) {
            List<Integer> isSet = Lists.newArrayList(queryForm.getIsSet());
            queryForm.getFilterMap().put("isSet", isSet);
        }*/
    }


    @Override
    public Page<Integer> findProductIds(PageRequest pageRequest, String keywords, Map<String, List<Integer>> filterMap) {
        return null;
    }

    @Override
    public EsProductEntity saveOrUpdateEntity(EsProductEntity productEntity) {
        return esProductRepository.save(productEntity);
    }

    @Override
    public void deleteProduct(Integer productId) {
        esProductRepository.delete(productId);
    }

    @Override
    public EsProductEntity findById(Integer productId) {
        return esProductRepository.findOne(productId);
    }

    @Override
    public EsProductEntity findByProductNumber(String productNumber) {
        return esProductRepository.findByProductNumber(productNumber);
    }

    @Override
    public Set<Map<String, String>> findByAutocomplete(String text, Pageable pageable) {
        //return esAutocompleteRepository.findByAutocommplete(text, pageable);
        return null;
    }
}

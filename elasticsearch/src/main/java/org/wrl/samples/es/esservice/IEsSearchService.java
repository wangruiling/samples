package org.wrl.samples.es.esservice;

import com.bluejean.store.web.es.aggregation.AggregationResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.wrl.samples.es.esentity.EsProductEntity;
import org.wrl.samples.es.form.EsSearchForm;
import org.wrl.samples.es.form.EsSearchResult;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2014-08-18 16:43
 */
public interface IEsSearchService {
    EsProductEntity findById(Integer productId);

    EsProductEntity findByProductNumber(String productNumber);

    /**
     * 前台搜索
     * @param queryForm
     * @return
     */
    EsSearchResult baseSearch(EsSearchForm queryForm);

    /**
     * 统计搜索结果
     * @param queryForm
     * @return
     */
    //List<FacetResult> aggregateForSearch(EsSearchForm queryForm);

    /**
     * 统计搜索结果
     * @param queryForm
     * @return
     */
    List<AggregationResult> aggregatesForSearch(EsSearchForm queryForm);

    /**
     * 根据productId集合查找数据
     * @param productIds
     * @return
     */
    List<EsProductEntity> findByProductIds(List<Integer> productIds);

    /**
     * 后台搜索使用，结果仅返回id集合
     * @param pageRequest 分页信息
     * @param keywords 关键词
     * @param filterMap 过滤信息集合
     * @return productId集合
     */
    Page<Integer> findProductIds(PageRequest pageRequest, String keywords, Map<String, List<Integer>> filterMap);

    EsProductEntity saveOrUpdateEntity(EsProductEntity productEntity);

    void deleteProduct(Integer productId);

    Set<Map<String, String>> findByAutocomplete(String text, Pageable pageable);

    /**
     * 最新上线图片数量
     * @return
     */
    long findLatestOnlineNumber();
}

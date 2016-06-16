package org.wrl.samples.es.esrepository;

import com.bluejean.store.web.es.aggregation.AggregationResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.wrl.samples.es.esentity.EsProductEntity;
import org.wrl.samples.es.form.EsSearchForm;
import org.wrl.samples.es.form.EsSearchResult;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * 自定义功能接口
 * @author: wangrl
 * @Date: 2014-08-15 15:46
 */
public interface EsProductRepositoryCustom {

    /**
     * 自定义分页查询
     * @param pageRequest
     * @param queryForm
     * @return
     */
    EsSearchResult queryByPage(PageRequest pageRequest, EsSearchForm queryForm);

    /**
     * 查找相似图片
     * @param pageRequest
     * @param productEntity
     * @return
     */
    Page<EsProductEntity> queryMoreLikeByPage(PageRequest pageRequest, EsProductEntity productEntity);

    /**
     * 根据供应商的原始编号进行搜索
     * @param pageRequest
     * @param queryForm
     * @param searchResult
     * @return
     */
    EsSearchResult findByOriginalNo(PageRequest pageRequest, EsSearchForm queryForm, EsSearchResult searchResult);

    /**
     * 获取图片数量
     * @param queryForm
     * @return
     */
    long countByQueryForm(EsSearchForm queryForm);

    /**
     * 统计搜索结果
     * @param queryForm
     * @return
     */
    List<AggregationResult> aggregateForSearch(EsSearchForm queryForm);
}

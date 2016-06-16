package org.wrl.samples.es.esrepository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.wrl.samples.es.esentity.EsProductEntity;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2014-08-15 14:28
 */
@Repository("esProductRepository")
public interface EsProductRepository extends ElasticsearchRepository<EsProductEntity, Integer>, EsProductRepositoryCustom{
    //    List<EsProductEntity> findByKeywords(String keywords);
    EsProductEntity findByProductNumber(String productNumber);
}

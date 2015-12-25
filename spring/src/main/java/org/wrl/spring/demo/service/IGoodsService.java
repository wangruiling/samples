package org.wrl.spring.demo.service;


import org.springframework.data.domain.Page;
import org.wrl.spring.demo.model.GoodsModel;
import org.wrl.spring.orm.commons.service.IBaseService;

public interface IGoodsService extends IBaseService<GoodsModel, Integer> {
    int DEFAULT_PAGE_SIZE = 10;

    /**根据页码查询所有已发布的商品的分页对象*/
    Page<GoodsModel> listAllPublished(int pn);
}

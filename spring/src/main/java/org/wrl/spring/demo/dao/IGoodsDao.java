package org.wrl.spring.demo.dao;

import org.wrl.spring.demo.model.GoodsModel;
import org.wrl.spring.orm.commons.dao.IBaseDao;

import java.util.List;

/**
 * Created by wrl on 2015/12/10.
 */
public interface IGoodsDao extends IBaseDao<GoodsModel, Integer> {
    /** 分页查询所有已发布的商品*/
    List<GoodsModel> listAllPublished(int pn);

    /** 统计所有已发布的商品记录数*/
    int countAllPublished();
}

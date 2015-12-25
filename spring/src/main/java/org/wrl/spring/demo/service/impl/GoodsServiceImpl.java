package org.wrl.spring.demo.service.impl;

import org.springframework.data.domain.Page;
import org.wrl.spring.demo.dao.IGoodsDao;
import org.wrl.spring.demo.model.GoodsModel;
import org.wrl.spring.demo.service.IGoodsService;
import org.wrl.spring.orm.commons.service.impl.BaseServiceImpl;
import org.wrl.spring.orm.commons.util.PageUtil;

import java.util.List;

public class GoodsServiceImpl extends BaseServiceImpl<GoodsModel, Integer> implements IGoodsService {

    @Override
    public Page<GoodsModel> listAllPublished(int pn) {
        int count = getGoodsDao().countAllPublished();
        List<GoodsModel> items = getGoodsDao().listAllPublished(pn);
        return PageUtil.getPage(count, pn, items, DEFAULT_PAGE_SIZE);
    }
    
    IGoodsDao getGoodsDao() {
        return (IGoodsDao) getDao();
    }

}

package org.wrl.spring.demo.dao;

import org.wrl.spring.demo.model.GoodsModel;
import org.wrl.spring.orm.commons.dao.hibernate.BaseHibernateDao;

import java.util.List;

/**
 * Created by wrl on 2015/12/10.
 */
public class GoodsHibernateDao extends BaseHibernateDao<GoodsModel, Integer> implements IGoodsDao {
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Override //覆盖掉父类的delete方法，不进行物理删除
    public void delete(Integer id) {
        GoodsModel goods = get(id);
        goods.setDeleted(true);
        update(goods);
    }

    @Override //覆盖掉父类的getCountAllHql方法，查询不包括逻辑删除的记录
    protected String getCountAllHql() {
        return super.getCountAllHql() + " where deleted=false";
    }

    @Override //覆盖掉父类的getListAllHql方法，查询不包括逻辑删除的记录
    protected String getListAllHql() {
        return super.getListAllHql() + " where deleted=false";
    }

    @Override //统计没有被逻辑删除的且发布的商品数量
    public int countAllPublished() {
        String hql = getCountAllHql() + " and published=true";
        Number result = unique(hql);
        return result.intValue();
    }

    @Override //查询没有被逻辑删除的且发布的商品
    public List<GoodsModel> listAllPublished(int pn) {
        String hql = getListAllHql() + " and published=true";
        return list(hql, pn, DEFAULT_PAGE_SIZE);
    }
}

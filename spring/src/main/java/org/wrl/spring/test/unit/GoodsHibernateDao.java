package org.wrl.spring.test.unit;

import org.springframework.orm.hibernate5.HibernateTemplate;

/**
 * Created by wrl on 2015/12/10.
 */
public class GoodsHibernateDao implements IGoodsDao{
    private HibernateTemplate hibernateTemplate;

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }
}

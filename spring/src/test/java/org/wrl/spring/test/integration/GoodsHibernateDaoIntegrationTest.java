package org.wrl.spring.test.integration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.wrl.spring.demo.dao.IGoodsDao;
import org.wrl.spring.demo.model.GoodsModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations={"classpath:applicationContext-hibernate.xml"})
//spring4.2之后@TransactionConfiguration废弃了，使用@Transactional和@Rollback进行代替
@Transactional(transactionManager = "txManager")
@Rollback(value = false)
public class GoodsHibernateDaoIntegrationTest {
    

    @Autowired
    private ApplicationContext ctx;
    
    @Autowired
    private IGoodsDao goodsDao;
    
    @Transactional
    @Rollback
    @Test
    public void testListAllPublishedSuccess() {
        GoodsModel goods = new GoodsModel();
        goods.setDeleted(false);
        goods.setDescription("");
        goods.setName("测试商品");
        goods.setPublished(true);
        goodsDao.save(goods);
        Assert.assertTrue(goodsDao.listAllPublished(1).size() == 1);
        Assert.assertTrue(goodsDao.listAllPublished(2).size() == 0);
    }



}

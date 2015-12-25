package org.wrl.spring.test.integration;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.wrl.spring.demo.model.GoodsModel;
import org.wrl.spring.demo.service.IGoodsService;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author: wangrl
 * @Date: 2015-12-25 23:00
 */
@ContextConfiguration(
        locations={"classpath:applicationContext-hibernate.xml"})
//spring4.2之后@TransactionConfiguration废弃了，使用@Transactional和@Rollback进行代替
@Transactional(transactionManager = "txManager")
@Rollback(value = false)
public class GoodsServiceImplIntegrationTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private IGoodsService goodsService;


    @Transactional
    @Rollback
    @Test
    public void testBuySuccess() {
        //1.添加商品
        GoodsModel goods = new GoodsModel();
        goods.setDeleted(false);
        goods.setDescription("");
        goods.setName("测试商品");
        goods.setPublished(true);
        goodsService.save(goods);

        assertThat(goods.getId()).isNotNull();
    }

}

package org.wrl.spring.test.unit;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.wrl.spring.demo.dao.IGoodsDao;
import org.wrl.spring.demo.model.GoodsModel;
import org.wrl.spring.demo.service.IGoodsService;
import org.wrl.spring.demo.service.impl.GoodsServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 业务逻辑层单元测试
 * @author: wangrl
 * @Date: 2015-12-24 23:49
 */
public class GoodsServiceImplUnitTest {
    //1、Mock对象上下文，用于创建Mock对象
    private final Mockery context = new Mockery() {{
        //1.1、表示可以支持Mock非接口类，默认只支持Mock接口
        setImposteriser(ClassImposteriser.INSTANCE);
    }};

    //2、Mock IGoodsCodeDao接口
    private IGoodsDao goodsDao = context.mock(IGoodsDao.class);;

    private IGoodsService goodsService;

    @Before
    public void setUp() {
        GoodsServiceImpl goodsServiceTemp = new GoodsServiceImpl();
        //3、依赖注入
        goodsServiceTemp.setDao(goodsDao);
        goodsService = goodsServiceTemp;

    }

    @Test(expected = Exception.class)
    public void testBuyFail() {
        final int goodsId = 1;
        //4、定义预期行为，并在后边来验证预期行为是否正确
        context.checking(new org.jmock.Expectations() {
            {
                //5、表示需要调用goodsCodeDao对象的getOneNotExchanged一次且仅一次
                //且返回值为null
                //oneOf(goodsDao).getOneNotExchanged(goodsId);
                will(returnValue(null));
            }
        });
        //goodsService.buy("test", goodsId);
        context.assertIsSatisfied();
    }

    @Test()
    public void testBuySuccess() {
        final int goodsId = 1;
        final GoodsModel goods = new GoodsModel();
        //6、定义预期行为，并在后边来验证预期行为是否正确
        context.checking(new org.jmock.Expectations() {
            {
                //7、表示需要调用goodsCodeDao对象的getOneNotExchanged一次且仅仅一次
                //且返回值为null
                //oneOf(goodsDao).getOneNotExchanged(goodsId);
                will(returnValue(goods));
                //8、表示需要调用goodsCodeDao对象的save方法一次且仅一次
                //且参数为goodsCode
                oneOf(goodsDao).save(goods);
            }
        });
        //goodsService.buy("test", goodsId);
        context.assertIsSatisfied();
        assertThat(goods.getId());
    }

}

package org.wrl.spring.test.unit;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Created by wrl on 2015/12/10.
 * 数据访问层单元测试通过Mock对象与数据库交互的API来完成测试
 */
public class GoodsHibernateDaoUnitTest {
    //1、Mock对象上下文，用于创建Mock对象
    private final Mockery context = new Mockery() {{
        //1.1、表示可以支持Mock非接口类，默认只支持Mock接口
        setImposteriser(ClassImposteriser.INSTANCE);
    }};

    //2、Mock HibernateTemplate类
    private final HibernateTemplate mockHibernateTemplate = context.mock(HibernateTemplate.class);
    private IGoodsDao goodsDao = null;

    @Before
    public void setUp() {
        //3、创建IGoodsDao实现
        GoodsHibernateDao goodsDaoTemp = new GoodsHibernateDao();
        //4、通过ReflectionTestUtils注入需要的非public字段数据
        ReflectionTestUtils.setField(goodsDaoTemp, "entityClass", GoodsModel.class);
        //5、注入mockHibernateTemplate对象
        goodsDaoTemp.setHibernateTemplate(mockHibernateTemplate);
        //6、赋值给我们要使用的接口
        goodsDao = goodsDaoTemp;
    }
}

package org.wrl.spring.test.unit;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import org.wrl.spring.demo.dao.GoodsHibernateDao;
import org.wrl.spring.demo.dao.IGoodsDao;
import org.wrl.spring.demo.model.GoodsModel;

import static org.assertj.core.api.Assertions.assertThat;

/**
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

    @Test
    public void testGet() {
        //7、创建需要的Model数据
        final GoodsModel expected = new GoodsModel();

        //8、定义预期行为，并在后边来验证预期行为是否正确
        context.checking(new org.jmock.Expectations() {
            {
                //9、表示需要调用且只调用一次mockHibernateTemplate的get方法，
                //且get方法参数为(GoodsModel.class, 1)，并将返回goods
                oneOf(mockHibernateTemplate).get(GoodsModel.class, 1);
                will(returnValue(expected));
            }
        });

        //10、调用goodsDao的get方法，在内部实现中将委托给getHibernateTemplate().get(this.entityClass, id);
        //因此按照第8步定义的预期行为将返回goods
        GoodsModel actual = goodsDao.get(1);
        //11、来验证第8步定义的预期行为是否调用了
        context.assertIsSatisfied();
        //12、验证goodsDao.get(1)返回结果是否正确
        assertThat(actual).isEqualTo(expected);
    }
}

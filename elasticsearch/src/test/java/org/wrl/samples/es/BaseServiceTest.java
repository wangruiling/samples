package org.wrl.samples.es;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.invoke.MethodHandles;

/**
 * Spring的支持数据库访问, 事务控制和依赖注入的JUnit4 集成测试基类.
 * @author: wangrl
 * @Date: 2015-02-06 14:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(inheritLocations = true, locations = {"classpath*:/spring-config-elasticsearch.xml"})
// 如果存在多个transactionManager，需显式指定,如需测试后事务回滚需把defaultRollback设为true
//@Rollback
//@Transactional
public abstract class BaseServiceTest {
    protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
}

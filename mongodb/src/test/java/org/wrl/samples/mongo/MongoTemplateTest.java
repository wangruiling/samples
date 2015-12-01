package org.wrl.samples.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wrl.samples.mongo.mentity.Customer;

/**
 * Created with IntelliJ IDEA.
 * spring-data-mongo测试
 * @author: wangrl
 * @Date: 2015-11-30 17:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/*.xml")
public class MongoTemplateTest {
    @Autowired
    private MongoTemplate mongoTemplate;


    @Test
    public void test(){
        Customer c = new Customer();
        c.setFirstName("wu");
        c.setLastName("wei");
        mongoTemplate.insert(c);
    }
}

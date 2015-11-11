package org.wrl.spring.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wrl.spring.aop.service.IHelloWorldService;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-11-11 16:09
 */
public class AopTest {
    @Test
    public void testHelloworld() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("ApplicationContext-Aop.xml");
        IHelloWorldService helloworldService = ctx.getBean("helloWorldService", IHelloWorldService.class);
        helloworldService.sayHello();
    }
}

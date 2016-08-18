package org.wrl.spring.AspectJ;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: wangrl
 * @Date: 2016-08-18 10:49
 */
public class TestMain {
    public static void main(String[] arge){
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext-Aspectj.xml");
        HelloworldService helloWorldService = context.getBean("helloWorldService", HelloworldService.class);

        helloWorldService.sayHello();
    }
}

package org.wrl.spring.spring3;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * Created by wrl on 2015/11/4.
 */
public class HelloImplTest {

    @Test
    public void testSayHello() throws Exception {
        //1、读取配置文件实例化一个IoC容器
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");

        //2、从容器中获取Bean，注意此处完全“面向接口编程，而不是面向实现”
        HelloApi helloApi = context.getBean("hello", HelloApi.class);

        //3、执行业务逻辑
        helloApi.sayHello();
    }
}
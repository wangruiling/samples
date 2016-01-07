package org.wrl.quartz.spring.demo1;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: wangrl
 * @Date: 2016-01-07 10:19
 */
public class AppMain {
    public static void main(String args[]){
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
    }
}

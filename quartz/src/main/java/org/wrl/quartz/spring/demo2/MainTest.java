package org.wrl.quartz.spring.demo2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 运行Quartz集群
 * 在相同或不同的机器上运行MainTest进行测试
 * @author: wangrl
 * @Date: 2016-01-07 16:08
 */
public class MainTest {
    public static void main(String[] args) {
        ApplicationContext springContext = new ClassPathXmlApplicationContext("classpath:spring-config-quartz-cluster.xml");
    }
}

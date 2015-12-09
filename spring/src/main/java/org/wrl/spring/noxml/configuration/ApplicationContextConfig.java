package org.wrl.spring.noxml.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;
import org.wrl.spring.spring3.*;

/**
 * 1、通过@Configuration注解需要作为配置的类，表示该类将定义Bean配置元数据
 * 2、通过@Bean注解相应的方法，该方法名默认就是Bean名，该方法返回值就是Bean对象
 * 3、通过AnnotationConfigApplicationContext或子类加载基于Java类的配置
 * Created by wrl on 2015/12/7.
 */
@Configuration
//使用@ImportResource引入基于XML方式的配置文件，如果有多个请使用@ImportResource({"config1.xml", "config2.xml"})方式指定多个配置文件
@ImportResource("classpath:/noxml-importResource.xml")
public class ApplicationContextConfig {
    @Bean
    public String message() {
        return "hello";
    }

    @Bean
    public HelloApi helloImpl3() {
        //通过构造器注入,分别是引用注入（message()）和常量注入（1）
        return new HelloImpl3(message(), 1);
    }

    @Bean
    public HelloApi helloImpl4() {
        HelloImpl4 helloImpl4 = new HelloImpl4();
        //通过setter注入注入引用
        helloImpl4.setMessage(message());
        //通过setter注入注入常量
        helloImpl4.setIndex(1);
        return helloImpl4;
    }

    @Bean
    @Scope("singleton")
    public HelloApi helloApi2() {
        HelloImpl5 helloImpl5 = new HelloImpl5() {
            @Override
            public Printer createPrototypePrinter() {
                //方法注入,注入原型Bean
                return prototypePrinter();
            }

            @Override
            public Printer createSingletonPrinter() {
                //方法注入,注入单例Bean
                return singletonPrinter();
            }
        };
        //依赖注入,注入单例Bean
        helloImpl5.setPrinter(singletonPrinter());
        return helloImpl5;
    }

    @Bean
    @Scope(value="prototype")
    public Printer prototypePrinter() {
        return new Printer();
    }

    @Bean
    @Scope(value="singleton")
    public Printer singletonPrinter() {
        return new Printer();
    }

}

package org.wrl.spring.noxml.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 1、通过@Configuration注解需要作为配置的类，表示该类将定义Bean配置元数据
 * 2、通过@Bean注解相应的方法，该方法名默认就是Bean名，该方法返回值就是Bean对象
 * 3、通过AnnotationConfigApplicationContext或子类加载基于Java类的配置
 * Created by wrl on 2015/12/7.
 */
@Configuration
public class ApplicationContextConfig {
    @Bean
    public String message() {
        return "hello";
    }
}

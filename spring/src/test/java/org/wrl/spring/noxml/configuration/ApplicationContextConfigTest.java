package org.wrl.spring.noxml.configuration;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wrl.spring.spring3.HelloApi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/**
 * Created by wrl on 2015/12/7.
 */
public class ApplicationContextConfigTest {

    @Test
    public void testMessage() throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
        assertThat(ctx.getBean("message")).isEqualTo("hello");
    }

    @Test
    public void  testDependencyInject() {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
        ctx.getBean("helloImpl3", HelloApi.class).sayHello();
        ctx.getBean("helloImpl4", HelloApi.class).sayHello();
    }

    @Test
    public void  testLookupMethodInject() {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
        System.out.println("=======prototype sayHello======");
        HelloApi helloApi2 = ctx.getBean("helloApi2", HelloApi.class);
        helloApi2.sayHello();
        helloApi2 = ctx.getBean("helloApi2", HelloApi.class);
        helloApi2.sayHello();
    }

    @Test
    public void  testImport() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationContextConfig2.class);
        assertThat(ctx.getBean("message")).isEqualTo("hello");
        assertThat(ctx.getBean("message2")).isEqualTo("hello2");
    }

    @Test
    public void  testImportResource() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
        assertThat(ctx.getBean("message3")).isEqualTo("test");
    }

    @Test
    public void  testXmlConfig() {
        String configLocations[] = {"noxml-xml-config.xml"};
        ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocations);
        assertThat(ctx.getBean("message")).isEqualTo("hello");
    }

    public void  testMultipleConfig() {
        AnnotationConfigApplicationContext ctx1 = new AnnotationConfigApplicationContext(
                        ApplicationContextConfig.class,
                        ApplicationContextConfig2.class);

        AnnotationConfigApplicationContext ctx2 = new AnnotationConfigApplicationContext();
        ctx2.register(ApplicationContextConfig.class);
        ctx2.register(ApplicationContextConfig2.class);
    }

    @Test
    public void  testComponentScan() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan("org.wrl.spring.noxml.configuration");
        ctx.refresh();
        assertThat(ctx.getBean("message")).isEqualTo("hello");

    }
}
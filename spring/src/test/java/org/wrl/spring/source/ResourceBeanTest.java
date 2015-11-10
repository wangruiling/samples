package org.wrl.spring.source;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ResourceLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/**
 * Created by wrl on 2015/11/9.
 */
public class ResourceBeanTest {
    @Test
    public void test() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        ResourceBean resourceBean = ctx.getBean(ResourceBean.class);
        ResourceLoader loader = resourceBean.getResourceLoader();
        assertThat(loader instanceof ApplicationContext).isTrue();
    }


}
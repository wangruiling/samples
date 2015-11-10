package org.wrl.spring.source;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by wrl on 2015/11/9.
 */
public class StringToResourceTest {
    @Test
    public void test() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        StringToResource resourceBean1 = ctx.getBean("resourceBean1", StringToResource.class);
        StringToResource resourceBean2 = ctx.getBean("resourceBean2", StringToResource.class);
        assertThat(resourceBean1.getResource() instanceof ClassPathResource).isTrue();
        assertThat(resourceBean2.getResource() instanceof ClassPathResource).isTrue();
    }
}
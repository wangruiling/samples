package org.wrl.spring.noxml.configuration;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
}
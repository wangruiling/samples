package org.wrl.spring.source;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-11-10 14:31
 */
public class ResourceInjectTest {
    @Test
    public void test() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        ResourceInjectBean resourceBean1 = ctx.getBean("injectBean", ResourceInjectBean.class);
        ResourceInjectBean resourceBean2 = ctx.getBean("injectBean2", ResourceInjectBean.class);
        assertThat(resourceBean1.getResource() instanceof ClassPathResource).isTrue();
        assertThat(resourceBean2.getResource() instanceof ClassPathResource).isTrue();
    }

    @Test
    public void testResourceArrayInject() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        ResourceArrayBean resourceBean1 = ctx.getBean("resourceArrayBean", ResourceArrayBean.class);
        ResourceArrayBean resourceBean2 = ctx.getBean("resourceArrayBean2", ResourceArrayBean.class);
        ResourceArrayBean resourceBean3 = ctx.getBean("resourceArrayBean3", ResourceArrayBean.class);

        assertThat(resourceBean1.getResources().length).isEqualTo(2);
        assertThat(resourceBean2.getResources().length > 1).isTrue();
        assertThat(resourceBean3.getResources().length > 1).isTrue();

    }
}

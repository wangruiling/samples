package org.wrl.spring.source;

import org.junit.Test;
import org.springframework.core.io.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wrl on 2015/11/9.
 */
public class ResourceLoadTest {
    @Test
    public void testResourceLoad() {
        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource("classpath:org/wrl/spring/source/test1.txt");
//验证返回的是ClassPathResource
        assertThat(resource.getClass()).isEqualTo(ClassPathResource.class);
        Resource resource2 = loader.getResource("file:org/wrl/spring/source/test1.txt");
//验证返回的是UrlResource
        assertThat(resource2.getClass()).isEqualTo(UrlResource.class);
        Resource resource3 = loader.getResource("org/wrl/spring/source/test1.txt");
//验证返默认可以加载ClasspathResource
        assertThat(resource3 instanceof ClassPathResource).isTrue();
    }
}

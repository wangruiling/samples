package org.wrl.spring.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * 注解风格的
 * http://jinnianshilongnian.iteye.com/blog/2001040
 * @author: wangrl
 * @Date: 2015-12-27 22:54
 */
public class SpringCacheConfig {
    @Bean
    public CacheManager cacheManager() {

        try {
            net.sf.ehcache.CacheManager ehcacheCacheManager
                    = new net.sf.ehcache.CacheManager(new ClassPathResource("ehcache.xml").getInputStream());

            EhCacheCacheManager cacheCacheManager = new EhCacheCacheManager(ehcacheCacheManager);
            return cacheCacheManager;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

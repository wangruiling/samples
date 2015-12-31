package org.wrl.spring.cache;

import org.junit.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wrl on 2015/12/27.
 */
public class SpringCacheTest {
    @Test
    public void test() throws IOException {
        //创建底层Cache
        net.sf.ehcache.CacheManager ehcacheManager
                = new net.sf.ehcache.CacheManager(new ClassPathResource("ehcache.xml").getInputStream());

        //创建Spring的CacheManager
        EhCacheCacheManager cacheCacheManager = new EhCacheCacheManager();
        //设置底层的CacheManager
        cacheCacheManager.setCacheManager(ehcacheManager);

        Long id = 1L;
        User user = new User(id, "zhang", "zhang@gmail.com");

        //根据缓存名字获取Cache
        Cache cache = cacheCacheManager.getCache("user");
        //往缓存写数据
        cache.put(id, user);
        //从缓存读数据
        assertThat(cache.get(id, User.class)).isNotNull();
    }
}
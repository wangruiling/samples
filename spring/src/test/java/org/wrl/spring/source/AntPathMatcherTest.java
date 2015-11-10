package org.wrl.spring.source;

import org.junit.Test;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * Ant 路径通配符支持“？”、“*”、“**”，注意通配符匹配不包括目录分隔符“/”
 * @author: wangrl
 * @Date: 2015-11-10 13:29
 */
public class AntPathMatcherTest {
    private PathMatcher pathMatcher = new AntPathMatcher();


    @Test
    public void testQuestionMark() {
        // “?”：匹配一个字符，如“config?.xml”将匹配“config1.xml”
        assertThat(pathMatcher.match("config?.xml", "config1.xml")).isTrue();
        assertThat(pathMatcher.match("config?.xml", "config12.xml")).isFalse();
        assertThat(pathMatcher.match("config?.xml", "config.xml")).isFalse();
    }

    @Test
    public void testOneAsterisk() {
        //“*” ： 匹配零个或多个字符串， 如 “cn/*/config.xml” 将匹配 “cn/javass/config.xml”,但不匹配匹配“cn/config.xml”；
        // 而“cn/config-*.xml”将匹配“cn/config-dao.xml” ；
        assertThat(pathMatcher.match("config-*.xml", "config-dao.xml")).isTrue();
        assertThat(pathMatcher.match("config-*.xml", "config-.xml")).isTrue();
        assertThat(pathMatcher.match("config-**.xml", "config-dao.xml")).isTrue();

        assertThat(pathMatcher.match("config-*.xml", "config-1/.xml")).isFalse();
        assertThat(pathMatcher.match("config-*.xml", "config-1/2.xml")).isFalse();

        assertThat(pathMatcher.match("/cn/*/config.xml", "/cn/javass/config.xml")).isTrue();

        assertThat(pathMatcher.match("/cn/*/config.xml", "/cn/config.xml")).isFalse();
        assertThat(pathMatcher.match("/cn/*/config.xml", "/cn//config.xml")).isFalse();
        assertThat(pathMatcher.match("/cn/*/config.xml", "/cn/javass/spring/config.xml")).isFalse();

    }

    @Test
    public void testTwoAsterisk() {
        // “**”：匹配路径中的零个或多个目录，如“cn/**/config.xml”将匹配“cn/config.xml”，也匹配“cn/javass/spring/config.xml”；
        // 而“cn/javass/config-**.xml”将匹配“cn/javass/config-dao.xml”，即把“**”当做两个“*”处理。
        assertThat(pathMatcher.match("/cn/**/config-*.xml", "/cn/javass/config-dao.xml")).isTrue();
        assertThat(pathMatcher.match("/cn/**/config-*.xml", "/cn/javass/spring/config-dao.xml")).isTrue();
        assertThat(pathMatcher.match("/cn/**/config-*.xml", "/cn/config-dao.xml")).isTrue();

    }
}

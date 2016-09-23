package org.wrl.redis;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: wangrl
 * @Date: 2016-09-23 16:42
 */
public class Test5 {
    @Test
    public void lambdaExpressions() {
        // lambda expression for condition
        assertTrue(() -> "".isEmpty(), "string should be empty");
        // lambda expression for assertion message
        assertEquals("foo", "foo", () -> "message is lazily evaluated");
    }

    /**
     * JUnit 5 支持分组断言（grouped assertions），所有的断言都会被执行，即使其中一个或多个断言失败
     * 新增的 assertAll ，可用于检查一组相关调用（Invocation）的结果，并判断断言是否失败，虽然无法短路但可输出所有结果的值
     */
    @Test
    public void groupedAssertions() {
        Dimension dim = new Dimension(800, 600);
        assertAll("dimension",
                () -> assertTrue(dim.getWidth() == 800, "width"),
                () -> assertTrue(dim.getHeight() == 600, "height"));
    }

    /**
     * JUnit 5使用assertThrows和equalsThrows断言
     * 如果所调用的方法没有指定异常，测试中它们都会失败。为了进一步断言异常的属性（例如消息中包含某些信息），此时还将返回 expectThrows
     */
    @Test
    public void exceptions() {
        // assert exception type
        assertThrows(RuntimeException.class, () -> {
            throw new NullPointerException();
        });
        // assert on the expected exception
        Throwable exception = expectThrows(RuntimeException.class, () -> {
            throw new NullPointerException("should not be null");
        });
        assertEquals("should not be null", exception.getMessage());
    }

    /**
     * JUnit 5支持Hamcrest匹配和AssertJ断言库，可以用它们来代替JUnit 5的方法
     */
    //@Test
    //public void emptyString() {
    //    // JUnit 5
    //    org.junit.gen5.api.Assertions.assertTrue("".isEmpty());
    //
    //    // AssertJ
    //    org.assertj.core.api.Assertions.assertThat("").isEmpty();
    //
    //    // Hamcrest
    //    org.hamcrest.MatcherAssert.assertThat("", isEmptyString());
    //}


    @Test
    @Disabled
    public void skippedTest() {
        // not executed
    }
    @Test
    @Tag("jenkins")
    public void jenkinsOnly() {
        // ...
    }

    @Test
    public void windowsOnly() {
        Assumptions.assumeTrue(System.getenv("OS").startsWith("Windows"));
        // ...
    }


}

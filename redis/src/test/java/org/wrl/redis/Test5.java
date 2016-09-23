package org.wrl.redis;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

/**
 * @author: wangrl
 * @Date: 2016-09-23 16:42
 */
public class Test5 {
    @Test
    void lambdaExpressions() {
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
    void groupedAssertions() {
        Dimension dim = new Dimension(800, 600);
        assertAll("dimension",
                () -> assertTrue(dim.getWidth() == 800, "width"),
                () -> assertTrue(dim.getHeight() == 600, "height"));
    }

    /**
     * JUnit 5使用assertThrows和equalsThrows断言,如果所调用的方法没有指定异常，测试中它们都会失败。
     * expectThrows还会返回抛出的异常实例，以用于后续的验证（例如消息中包含某些信息），比如，断言异常信息包含正确的信息等
     */
    @Test
    void exceptions() {
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
    @Disabled("禁用测试")
    void skippedTest() {
        // not executed
        System.out.println("禁用测试");
    }
    @Test
    @Tag("jenkins")
    void jenkinsOnly() {
        // ...
    }

    @Test
    void windowsOnly() {
        assumeTrue(System.getenv("OS").startsWith("Windows"));
        // ...
    }

    @Test
    void exitIfFalseIsTrue() {
        assumeTrue(false);
        System.exit(1);
    }

    /*************** 假言/判定允许你仅在特定条件满足时才运行测试。这个特性能够减少测试组件的运行时间和代码重复，特别是在假言都不满足的情况下********/
    @Test
    void exitIfTrueIsFalse() {
        assumeFalse(this::truism);
        System.exit(1);
    }

    private boolean truism() {
        return true;
    }

    @Test
    void exitIfNullEqualsString() {
        assumingThat(
                "null".equals(null),
                () -> System.exit(1)
        );
    }
}

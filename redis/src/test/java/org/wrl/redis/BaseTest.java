package org.wrl.redis;

import org.junit.jupiter.api.*;
import redis.clients.jedis.Jedis;


/**
 * Created by wangrl on 2016/9/22.
 */
public abstract class BaseTest {
    protected static Jedis conn;

    /**
     * 只执行一次，执行时机是在所有测试和 @BeforeEach 注解方法之前
     */
    @BeforeAll
    public static void initAll() {
        conn = new Jedis("localhost");
    }

    /**
     * 在每个测试执行之前执行
     */
    @BeforeEach
    void init() {
        System.out.println("在每个测试执行之前执行");
    }

    /**
     * 在每个测试执行之后执行
     */
    @AfterEach
    void tearDown() {
        System.out.println("在每个测试执行之后执行");
    }

    /**
     * 只执行一次，执行时机是在所有测试和 @AfterEach 注解方法之后
     */
    @AfterAll
    protected static void tearDownAll() {
        conn.close();
    }
}

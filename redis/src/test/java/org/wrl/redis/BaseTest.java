package org.wrl.redis;

import org.junit.jupiter.api.*;
import redis.clients.jedis.Jedis;


/**
 * Created by wangrl on 2016/9/22.
 */
public abstract class BaseTest {
    protected static Jedis conn;

    @BeforeAll
    public static void initAll() {
        conn = new Jedis("localhost");
    }

    @BeforeEach
    void init() {
    }

    @AfterEach
    void tearDown() {
    }

    @AfterAll
    protected static void tearDownAll() {
        conn.close();
    }
}

package org.wrl.redis;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import redis.clients.jedis.Jedis;

/**
 * Created by wangrl on 2016/9/22.
 */
public abstract class BaseTest {
    protected static Jedis conn;

    @BeforeAll
    public static void setUp() {
        conn = new Jedis("localhost");
    }

    @AfterAll
    public static void down() {
        conn.close();
    }
}

package org.wrl.redis;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import redis.clients.jedis.Jedis;

/**
 * Created by wangrl on 2016/9/22.
 */
public abstract class BaseTest {
    protected static Jedis conn;

    @BeforeClass
    public static void setUp() {
        conn = new Jedis("localhost");
    }

    @AfterClass
    public static void down() {
        conn.close();
    }
}

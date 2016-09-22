package org.wrl.redis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

/**
 * Redis中的字符串
 * Created by wangrl on 2016/9/11.
 */
public class StringTest extends BaseTest {

    @Test
    public void testSet() {
        //相当于[set hello world]
        conn.set("hello", "world");
    }


    @Test
    public void testGet() {
        //相当于[get hello]
        String result = conn.get("hello");
        System.out.println("result:" + result);
    }

    @Test
    public void testDel() {
        //相当于[del hello]
        conn.del("hello");
    }
}

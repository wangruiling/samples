package org.wrl.redis;

import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * Redis 的散列存储的值可以是字符串又可以是数字值，并可以对散列存储的数字值执行自增操作或自减操作
 * Created by wangrl on 2016/9/25.
 */
public class HashTest extends BaseTest{
    /**
     * 在散列里关联起给定的键值对，相当于【hset hash-key sub-key1 value1】
     */
    @Test
    public void testHset() {
        long result = conn.hset("hset-key", "sub-key1", "value1");
        System.out.println("result:" + result);
    }

    /**
     * 获取指定散列键的值，相当于【hget hash-key sub-key1】
     */
    @Test
    public void testHget() {
        String result = conn.hget("hset-key", "sub-key1");
        System.out.println("result:" + result);
    }

    /**
     * 获取散列包含的所有的值，相当于【hgetall hash-key】
     */
    @Test
    public void testHgetall() {
        Map<String, String> result = conn.hgetAll("hset-key");
        System.out.println("result:" + result);
    }

    /**
     * 如果给定的键存在，那么移除这个键，相当于【hgetall hash-key】
     */
    @Test
    public void testHdel() {
        long result = conn.hdel("hset-key", "sub-key1");
        System.out.println("result:" + result);
    }
}

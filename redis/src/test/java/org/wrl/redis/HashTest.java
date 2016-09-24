package org.wrl.redis;

import org.junit.jupiter.api.Test;

/**
 * Redis 的散列存储的值可以是字符串又可以是数字值，并可以对散列存储的数字值执行自增操作或自减操作
 * Created by wangrl on 2016/9/25.
 */
public class HashTest extends BaseTest{
    /**
     * 在散列里关联起给定的键值对，相当于【hset hash-key sub-key1 value1】
     */
    @Test
    public void testRpush() {
        //在向列表推入新元素之后，该命令会返回列表当前的长度
        long length = conn.hset("hset-key", "sub-key1", "value1");
        System.out.println("length:" + length);
    }
}

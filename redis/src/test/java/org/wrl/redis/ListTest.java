package org.wrl.redis;


import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Created by wangrl on 2016/9/22.
 */
public class ListTest extends BaseTest {

    /**
     * 将给定的值推入列表的右端，相当于【rpush list-key item】
     */
    @Test
    public void testRpush() {
        //在向列表推入新元素之后，该命令会返回列表当前的长度
        long length = conn.rpush("list-key", "item");
        System.out.println("length:" + length);
    }

    /**
     * 获取列表在给定范围上的所有值，相当于【lrange list-key 0 -1】
     */
    @Test
    public void testLrange() {
        //使用0为范围的起始索引，-1为范围的结束
        List<String> elements = conn.lrange("list-key", 0, -1);
        System.out.println("elements:" + elements);
    }
}

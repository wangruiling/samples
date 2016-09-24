package org.wrl.redis;

import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * Created by wangrl on 2016/9/24.
 */
public class SetTest extends BaseTest {
    /**
     * 将给定的元素添加到集合，相当于【sadd set-key item】
     */
    @Test
    public void testSadd() {
        //返回1表示元素被成功添加进集合，返回0表示这个元素已经存在于集合中
        long result = conn.sadd("set-key", "item");
        System.out.println("result:" + result);
    }

    /**
     * 返回集合包含的所有元素，相当于【smembers set-key】
     */
    @Test
    public void testSmembers() {
        Set<String> result = conn.smembers("set-key");
        System.out.println("result:" + result);
    }

    /**
     * 检查给定元素是否存在与集合中，相当于【sismember set-key item】
     */
    @Test
    public void testSismember() {
        boolean result = conn.sismember("set-key", "item");
        System.out.println("result:" + result);
    }

    /**
     * 如果给定的元素存在与集合中，那么移除这个元素，相当于【srem set-key item】
     */
    @Test
    public void testSrem() {
        //在使用命令移除集合中的元素时，命令会返回被移除元素的数量
        long result = conn.srem("set-key", "item");
        System.out.println("result:" + result);
    }

}

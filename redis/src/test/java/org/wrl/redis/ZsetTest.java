package org.wrl.redis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Tuple;

import java.util.Set;

/**
 *
 * Created by wangrl on 2016/9/25.
 */
public class ZsetTest extends BaseTest {
    /**
     * 将一个带有给定分值的成员添加到有序集合里面，相当于【zadd zset-key 728 member1】
     */
    @Test
    public void testZadd() {
        long result = conn.zadd("zset-key", 728, "member1");
        System.out.println("result:" + result);
    }

    /**
     * 根据元素在有序排列中所处的位置，从有序集合里获取，相当于【zrange zset-key 0 -1】
     */
    @Test
    public void testZrange() {
        Set<String> result = conn.zrange("zset-key", 0, -1);
        System.out.println("result:" + result);
    }

    /**
     * 根据元素在有序排列中所处的位置，从有序集合里获取，相当于【zrange zset-key 0 -1 withscores】
     */
    @Test
    public void testZrangeWithScores() {
        Set<redis.clients.jedis.Tuple> result = conn.zrangeWithScores("zset-key", 0, -1);
        System.out.println("result:" + result);
    }

    /**
     * 获取有序集合在给定分值范围内的所有元素，相当于【zrangebyscore zset-key 0 800】
     */
    @Test
    public void testZrangebyscore() {
        Set<String> result = conn.zrangeByScore("zset-key", 0, 800);
        System.out.println("result:" + result);
    }

    /**
     * 获取有序集合在给定分值范围内的所有元素，相当于【zrangebyscore zset-key 0 800 withscores】
     */
    @Test
    public void testZrangebysc() {
        Set<Tuple> result = conn.zrangeByScoreWithScores("zset-key", 0, 800);
        System.out.println("result:" + result);
    }

    /**
     * 如果给定的成员存在于有序集合，那么移除这个成员，相当于【zrangebyscore zset-key 0 800】
     */
    @Test
    public void testZrem() {
        long result = conn.zrem("zset-key", "member1");
        System.out.println("result:" + result);
    }
}

package com.linkedkeeper.redis.client;

import com.linkedkeeper.redis.client.base.BaseTest;
import org.junit.Test;

/**
 * @Author: zhangsr
 */
public class TestClient extends BaseTest {

    private RedisClient redisClient;

    @Test
    public void test() {
        System.out.println("start init ...");
        redisClient.sadd("test", "a");
    }

    //----------------- setter -----------------


    public void setRedisClient(RedisClient redisClient) {
        this.redisClient = redisClient;
    }
}

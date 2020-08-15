package com.heqing.demo.spring.redisson;

import com.heqing.demo.spring.redisson.config.SpringRedissonConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = SpringRedissonConfig.class
)
@ActiveProfiles("single")
public class RedissonTest {

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void test() {
        RBucket key = redissonClient.getBucket("redissonType");
        key.set("single");

        key = redissonClient.getBucket("redissonType");
        System.out.println("-->"+key.get());
    }
}

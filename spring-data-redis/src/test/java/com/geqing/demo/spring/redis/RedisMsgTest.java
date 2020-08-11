package com.geqing.demo.spring.redis;

import com.heqing.demo.spring.redis.config.SpringJedisConfig;
import com.heqing.demo.spring.redis.config.SpringLettuceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {SpringLettuceConfig.class, SpringJedisConfig.class}
)
@ActiveProfiles({"jedis","single"})
public class RedisMsgTest {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    public void sendMsg() {
        redisTemplate.convertAndSend("test", "heqing");
    }

}

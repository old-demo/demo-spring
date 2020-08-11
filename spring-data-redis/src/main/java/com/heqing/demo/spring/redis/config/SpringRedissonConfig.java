package com.heqing.demo.spring.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;

import java.util.Set;

@Configuration
@ComponentScan("com.heqing.demo.spring.redis.*")
public class SpringRedissonConfig {

    @Profile("single")
    @Bean
    RedissonClient redissonSingle(RedisProperty redisProperty) {
        // 单机模式自动装配
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(redisProperty.getHost()+":"+redisProperty.getPort())
                .setTimeout(redisProperty.getTimeout());

        if(!StringUtils.isEmpty(redisProperty.getPassword())) {
            serverConfig.setPassword(redisProperty.getPassword());
        }
        return Redisson.create(config);
    }

    @Profile("sentinel")
    @Bean
    RedissonClient redissonSentinel(RedisProperty redisProperty) {
        // 哨兵模式自动装配
        Set<String> sentinelNodes = redisProperty.getSentinelNodes();
        Config config = new Config();
        SentinelServersConfig serverConfig = config.useSentinelServers()
                .addSentinelAddress(sentinelNodes.toArray(new String[sentinelNodes.size()]))
                .setMasterName(redisProperty.getMaster())
                .setTimeout(redisProperty.getTimeout());

        if(!StringUtils.isEmpty(redisProperty.getPassword())) {
            serverConfig.setPassword(redisProperty.getPassword());
        }
        return Redisson.create(config);
    }

    @Profile("cluster")
    @Bean
    RedissonClient redissonCluster(RedisProperty redisProperty) {
        // 哨兵模式自动装配
        Set<String> sentinelNodes = redisProperty.getSentinelNodes();
        Config config = new Config();
        ClusterServersConfig serverConfig = config.useClusterServers()
                .addNodeAddress(sentinelNodes.toArray(new String[sentinelNodes.size()]))
                .setTimeout(redisProperty.getTimeout());

        if(!StringUtils.isEmpty(redisProperty.getPassword())) {
            serverConfig.setPassword(redisProperty.getPassword());
        }
        return Redisson.create(config);
    }
}

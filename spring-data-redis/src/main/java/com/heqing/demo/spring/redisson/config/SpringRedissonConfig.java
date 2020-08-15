package com.heqing.demo.spring.redisson.config;

import com.heqing.demo.spring.redis.config.RedisProperty;
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
@ComponentScan("com.heqing.demo.spring.redisson.*")
public class SpringRedissonConfig {

    @Profile("single")
    @Bean
    RedissonClient redissonSingle(RedissonProperty redissonProperty) {
        // 单机模式自动装配
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress("redis://"+redissonProperty.getHost()+":"+redissonProperty.getPort())
                .setTimeout(redissonProperty.getTimeout());

        if(!StringUtils.isEmpty(redissonProperty.getPassword())) {
            serverConfig.setPassword(redissonProperty.getPassword());
        }
        return Redisson.create(config);
    }

    @Profile("sentinel")
    @Bean
    RedissonClient redissonSentinel(RedissonProperty redissonProperty) {
        // 哨兵模式自动装配
        Set<String> sentinelNodes = redissonProperty.getSentinelNodes();
        String[] address = new String[sentinelNodes.size()];
        int i = 0;
        for(String node : sentinelNodes) {
            address[i] = "redis://"+node;
            i++;
        }
        Config config = new Config();
        SentinelServersConfig serverConfig = config.useSentinelServers()
                .addSentinelAddress(address)
                .setMasterName(redissonProperty.getMaster())
                .setTimeout(redissonProperty.getTimeout());

        if(!StringUtils.isEmpty(redissonProperty.getPassword())) {
            serverConfig.setPassword(redissonProperty.getPassword());
        }
        return Redisson.create(config);
    }

    @Profile("cluster")
    @Bean
    RedissonClient redissonCluster(RedissonProperty redissonProperty) {
        // 哨兵模式自动装配
        Set<String> clusterNodes = redissonProperty.getClusterNodes();
        String[] address = new String[clusterNodes.size()];
        int i = 0;
        for(String node : clusterNodes) {
            address[i] = "redis://"+node;
            i++;
        }
        Config config = new Config();
        ClusterServersConfig serverConfig = config.useClusterServers()
                .addNodeAddress(address)
                .setTimeout(redissonProperty.getTimeout());

        if(!StringUtils.isEmpty(redissonProperty.getPassword())) {
            serverConfig.setPassword(redissonProperty.getPassword());
        }
        return Redisson.create(config);
    }
}

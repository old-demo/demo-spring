package com.heqing.demo.spring.redisson.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Set;

@Data
@Configuration
@PropertySource(value = "classpath:redis.properties")
public class RedissonProperty {

    @Value("${redis.pool.timeout}")
    private int timeout;

    @Value("${redis.pool.maxTotal}")
    private int maxTotal;

    @Value("${redis.pool.maxIdle}")
    private int maxIdle;

    @Value("${redis.pool.minIdle}")
    private int minIdle;

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.sentinel.master}")
    private String master;

    @Value("#{'${redis.sentinel.nodes}'.split(',')}")
    private Set<String> sentinelNodes;

    @Value("#{'${redis.cluster.nodes}'.split(',')}")
    private Set<String> clusterNodes;

}

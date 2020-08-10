package com.heqing.demo.spring.redis.lettuce.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Set;

@Data
@Configuration
@PropertySource(value = "classpath:redis.properties")
public class LettuceProperty {

    @Value("${lettuce.pool.maxTotal}")
    private int maxTotal;

    @Value("${lettuce.pool.maxIdle}")
    private int maxIdle;

    @Value("${lettuce.pool.minIdle}")
    private int minIdle;

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.password}")
    private String password;

    @Value("${lettuce.sentinel.master}")
    private String master;

    @Value("#{'${lettuce.sentinel.nodes}'.split(',')}")
    private Set<String> sentinelNodes;

    @Value("#{'${lettuce.cluster.nodes}'.split(',')}")
    private Set<String> clusterNodes;

}

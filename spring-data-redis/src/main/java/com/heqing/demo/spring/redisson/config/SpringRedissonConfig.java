package com.heqing.demo.spring.redisson.config;

import com.heqing.demo.spring.redisson.msg.PatternTopicDemo;
import com.heqing.demo.spring.redisson.msg.TopicDemo;
import org.redisson.Redisson;
import org.redisson.api.RPatternTopic;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.redisson.connection.AddressResolverGroupFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.StringUtils;

import java.util.Set;

@Configuration
@EnableAsync
@ComponentScan("com.heqing.demo.spring.redisson.*")
public class SpringRedissonConfig {

    @Autowired
    FastjsonCodec fastjsonCodec;

    @Profile("single")
    @Bean
    RedissonClient redissonSingle(RedissonProperty redissonProperty) {
        // 单机模式自动装配
        Config config = new Config();
        config.setCodec(fastjsonCodec);
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
        config.setCodec(fastjsonCodec);
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
        config.setCodec(fastjsonCodec);
        ClusterServersConfig serverConfig = config.useClusterServers()
                .addNodeAddress(address)
                .setTimeout(redissonProperty.getTimeout());

        if(!StringUtils.isEmpty(redissonProperty.getPassword())) {
            serverConfig.setPassword(redissonProperty.getPassword());
        }
        return Redisson.create(config);
    }

    @Bean
    public boolean topic(RedissonClient redissonClient) {
        // todo 测试订阅消息...怎么变成异步呢?

        RTopic topic = redissonClient.getTopic("testTopic");
        topic.addListener(String.class, new TopicDemo());

        RPatternTopic topicList = redissonClient.getPatternTopic("test*");
        topicList.addListener(String.class, new PatternTopicDemo());
        return true;
    }
}

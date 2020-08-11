package com.heqing.demo.spring.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ComponentScan("com.heqing.demo.spring.redis.*")
public class SpringJedisConfig {

    @Profile("single")
    @Bean
    public JedisConnectionFactory singleJedisFactory(RedisProperty redisProperty) {
        // 单机redis配置
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisProperty.getHost(), redisProperty.getPort());
//        configuration.setPassword(redisProperty.getPassword());
        JedisConnectionFactory factory = new JedisConnectionFactory(configuration);
        return factory;
    }

    @Profile("sentinel")
    @Bean
    public JedisConnectionFactory sentinelJedisFactory(RedisProperty redisProperty) {
        // 哨兵模式redis配置
        RedisSentinelConfiguration configuration = new RedisSentinelConfiguration(redisProperty.getMaster(), redisProperty.getSentinelNodes());
        configuration.setPassword(redisProperty.getPassword());
        JedisConnectionFactory factory = new JedisConnectionFactory(configuration);
        return factory;
    }

    @Profile("cluster")
    @Bean
    public JedisConnectionFactory clusterJedisFactory(RedisProperty redisProperty) {
        // 集群模式redis配置
        JedisClientConfiguration clientConfig = JedisClientConfiguration.builder().build();
        RedisClusterConfiguration serverConfig = new RedisClusterConfiguration(redisProperty.getClusterNodes());
        serverConfig.setPassword(redisProperty.getPassword());
        // 通过配置RedisStandaloneConfiguration实例来
        //创建Redis Standolone模式的客户端连接创建工厂
        //配置hostname和port
        JedisConnectionFactory factory = new JedisConnectionFactory(serverConfig, clientConfig);
        return factory;
    }

    @Profile("jedis")
    @Bean
    public RedisTemplate<String, Object> redisRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        // key序列化
        redisTemplate.setKeySerializer(stringSerializer);
        // value序列化
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Profile("jedis")
    @Bean
    public RedisMessageListenerContainer container(JedisConnectionFactory connectionFactory,  MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("test"));
        return container;
    }
}

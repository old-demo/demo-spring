package com.heqing.demo.spring.redis.config;

import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.time.Duration;

import static java.util.Collections.singletonMap;

@Configuration
@ComponentScan("com.heqing.demo.spring.redis.*")
@EnableCaching
public class SpringLettuceConfig extends CachingConfigurerSupport {

    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuffer sb = new StringBuffer();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    //由于参数可能不同, 缓存的key也需要不一样
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    /**
     * 缓存管理器
     */
    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
        /* 默认配置， 默认超时时间为30分钟=30*60s=1800s */
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(1800L)).disableCachingNullValues();
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(RedisCacheWriter.lockingRedisCacheWriter(lettuceConnectionFactory))
                .cacheDefaults(defaultCacheConfig)
                .withInitialCacheConfigurations(singletonMap("business_data", RedisCacheConfiguration.defaultCacheConfig() .entryTtl(Duration.ofSeconds(3600L)) .disableCachingNullValues()))
                .withInitialCacheConfigurations(singletonMap("system_data", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(3600L)).disableCachingNullValues()))
                .withInitialCacheConfigurations(singletonMap("common_data", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(3600L)).disableCachingNullValues()))
                .withInitialCacheConfigurations(singletonMap("test_data", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(60L)).disableCachingNullValues()))
                .transactionAware();
        RedisCacheManager cacheManager = builder.build();
        return cacheManager;
    }

    @Profile("single")
    @Bean
    public LettuceConnectionFactory singleLettuceFactory(RedisProperty redisProperty) {
        // 单机redis配置
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisProperty.getHost(), redisProperty.getPort());
        if(!StringUtils.isEmpty(redisProperty.getPassword())) {
            configuration.setPassword(redisProperty.getPassword());
        }
        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration);
        factory.setValidateConnection(true);
        return factory;
    }

    @Profile("sentinel")
    @Bean
    public LettuceConnectionFactory sentinelLettuceFactory(RedisProperty redisProperty) {
        // 哨兵模式redis配置
        RedisSentinelConfiguration configuration = new RedisSentinelConfiguration(redisProperty.getMaster(), redisProperty.getSentinelNodes());
        configuration.setPassword(redisProperty.getPassword());
        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration);
        factory.setValidateConnection(true);
        return factory;
    }

    @Profile("cluster")
    @Bean
    public LettuceConnectionFactory clusterLettuceFactory(RedisProperty redisProperty) {
        // 集群模式redis配置
        ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                .enablePeriodicRefresh(Duration.ofSeconds(30))
                .enableAllAdaptiveRefreshTriggers()
                .build();
        ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
                .validateClusterNodeMembership(false)
                .topologyRefreshOptions(clusterTopologyRefreshOptions)
                .build();
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .shutdownTimeout(Duration.ZERO)
                .clientOptions(clusterClientOptions)
                .build();

        RedisClusterConfiguration serverConfig = new RedisClusterConfiguration(redisProperty.getClusterNodes());

        serverConfig.setPassword(redisProperty.getPassword());
        // 通过配置RedisStandaloneConfiguration实例来
        //创建Redis Standolone模式的客户端连接创建工厂
        //配置hostname和port
        LettuceConnectionFactory factory = new LettuceConnectionFactory(serverConfig, clientConfig);
        factory.setValidateConnection(true);
        return factory;
    }

    @Profile("lettuce")
    @Bean
    public RedisTemplate<String, Object> redisLettuceTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        // 设置序列化
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        // key序列化
        redisTemplate.setKeySerializer(stringSerializer);
        // value序列化
        redisTemplate.setValueSerializer(stringSerializer);
        // Hash key序列化
        redisTemplate.setHashKeySerializer(stringSerializer);
        // Hash value序列化
        redisTemplate.setHashValueSerializer(stringSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Profile("lettuce")
    @Bean
    public RedisMessageListenerContainer container(LettuceConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("test"));
        return container;
    }
}

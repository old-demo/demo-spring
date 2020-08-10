package com.heqing.demo.spring.redis.lettuce.config;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;

import static java.util.Collections.singletonMap;

@Configuration
@EnableCaching // 开启缓存支持
@ComponentScan("com.heqing.demo.spring.redis.*")
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

    @Bean
    @Profile("single")
    public LettuceConnectionFactory singleLettuceFactory(LettuceProperty lettuceProperty) {
        // 单机redis配置
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(lettuceProperty.getHost(), lettuceProperty.getPort());
        configuration.setPassword(lettuceProperty.getPassword());
        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration);
        factory.setValidateConnection(true);
        return factory;
    }

    @Bean
    @Profile("sentinel")
    public LettuceConnectionFactory sentinelLettuceFactory(LettuceProperty lettuceProperty) {
        // 哨兵模式redis配置
        RedisSentinelConfiguration configuration = new RedisSentinelConfiguration(lettuceProperty.getMaster(), lettuceProperty.getSentinelNodes());
        configuration.setPassword(lettuceProperty.getPassword());
        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration);
        factory.setValidateConnection(true);
        return factory;
    }

    @Bean
    @Profile("cluster")
    public LettuceConnectionFactory lettuceConnectionFactory(LettuceProperty lettuceProperty) {

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

        RedisClusterConfiguration serverConfig = new RedisClusterConfiguration(lettuceProperty.getClusterNodes());

        serverConfig.setPassword(lettuceProperty.getPassword());
        // 通过配置RedisStandaloneConfiguration实例来
        //创建Redis Standolone模式的客户端连接创建工厂
        //配置hostname和port
        LettuceConnectionFactory factory = new LettuceConnectionFactory(serverConfig, clientConfig);
        factory.setValidateConnection(true);
        return factory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        // 设置序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        // 对象映射
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        // key序列化
        redisTemplate.setKeySerializer(stringSerializer);
        // value序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // Hash key序列化
        redisTemplate.setHashKeySerializer(stringSerializer);
        // Hash value序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}

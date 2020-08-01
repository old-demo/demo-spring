package com.heqing.demo.spring.hibernate.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:jdbc.properties")
@Data
public class JDBCConfig {

    @Value("${jdbcUrl}")
    private String url;

    @Value("${driverClass}")
    private String driverClass;

    @Value("${user}")
    private String user;

    @Value("${password}")
    private String password;

    @Value("${initialSize:5}")
    private Integer initialSize;

    @Value("${minIdle:5}")
    private Integer minIdle;

    @Value("${maxActive:50}")
    private Integer maxActive;

    @Value("${maxWait:60000}")
    private Integer maxWait;

    @Value("${validationQuery:SELECT 'x'}")
    private String validationQuery;

    @Value("${testWhileIdle:true}")
    private Boolean testWhileIdle;

    @Value("${testOnBorrow:false}")
    private Boolean testOnBorrow;

    @Value("${testOnReturn:false}")
    private Boolean testOnReturn;

    @Value("${timeBetweenEvictionRunsMillis:60000}")
    private Integer timeBetweenEvictionRunsMillis;

    @Value("${minEvictableIdleTimeMillis:300000}")
    private Integer minEvictableIdleTimeMillis;

    @Value("${poolPreparedStatements:true}")
    private Boolean poolPreparedStatements;

    @Value("${maxPoolPreparedStatementPerConnectionSize:20}")
    private Integer maxPoolPreparedStatementPerConnectionSize;
}

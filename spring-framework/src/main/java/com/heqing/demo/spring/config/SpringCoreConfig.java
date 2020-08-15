package com.heqing.demo.spring.config;

import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAspectJAutoProxy     // 启动AspectJ自动代理
@EnableAsync
@ComponentScan("com.heqing.demo.spring.*")    // 启动自动扫描bean
public class SpringCoreConfig {

    @Bean
    @Profile("dev")
    public void devProfile() {
        System.out.println("--- dev ---");
    }

    @Bean
    @Profile("test")
    public void testProfile() {
        System.out.println("--- test ---");
    }

    @Bean
    @Profile("stg")
    public void stgProfile() {
        System.out.println("--- stg ---");
    }

    @Bean
    @Profile("prod")
    public void prodProfile() {
        System.out.println("--- prod ---");
    }
}

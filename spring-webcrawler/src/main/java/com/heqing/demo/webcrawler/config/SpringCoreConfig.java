package com.heqing.demo.webcrawler.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy     // 启动AspectJ自动代理
@ComponentScan("com.heqing.demo.webcrawler.*")    // 启动自动扫描bean
public class SpringCoreConfig {
}

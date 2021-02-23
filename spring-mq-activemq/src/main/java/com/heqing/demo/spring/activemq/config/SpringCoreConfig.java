package com.heqing.demo.spring.activemq.config;

import org.springframework.context.annotation.*;

@Configuration
@EnableAspectJAutoProxy     // 启动AspectJ自动代理
@ComponentScan("com.heqing.demo.spring.*")    // 启动自动扫描bean
public class SpringCoreConfig {

}

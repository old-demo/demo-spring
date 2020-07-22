package com.heqing.demo.spring.config.env;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@PropertySource({"classpath:env/env-dev.properties"})  //  引入配置文件
public class EnvDevConfig {
}

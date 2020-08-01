package com.heqing.demo.spring.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource({"classpath:env/env.properties", "classpath:env/env-${env}.properties"})
public class EnvConfig {
}

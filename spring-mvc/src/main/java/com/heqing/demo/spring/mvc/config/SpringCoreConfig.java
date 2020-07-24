package com.heqing.demo.spring.mvc.config;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "com.heqing.demo.spring.*",
        excludeFilters = {@ComponentScan.Filter(type=FilterType.ANNOTATION, value = EnableWebMvc.class)})
public class SpringCoreConfig {

}

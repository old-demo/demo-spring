package com.heqing.demo.spring.apollo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.context.annotation.*;

@Configuration
@EnableApolloConfig(value = "application", order = 10)
@ComponentScan("com.heqing.demo.spring.*")    // 启动自动扫描bean
public class AppConfig {

}

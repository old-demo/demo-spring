package com.heqing.demo.spring.core;

import com.heqing.demo.spring.config.SpringCoreConfig;
import com.heqing.demo.spring.core.service.EnvService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = SpringCoreConfig.class
)
@ActiveProfiles("dev")
public class TestEnv {

    @Autowired
    EnvService envService;

    @Test
    public void testProfile() {
        envService.placeholder();
        envService.spel();
    }
}

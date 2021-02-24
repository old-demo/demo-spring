package com.heqing.demo.spring.dubbo;

import com.heqing.demo.spring.dubbo.service.DemoConsumerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    locations = {"classpath*:spring_core.xml", "classpath*:dubbo_consumer.xml"}
)
public class DubboConsumerTest {

    @Autowired
    DemoConsumerService demoConsumerService;

    @Test
    public void testDubbo() throws IOException {
        System.in.read();
    }

}

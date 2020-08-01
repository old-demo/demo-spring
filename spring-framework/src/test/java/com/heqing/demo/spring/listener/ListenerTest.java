package com.heqing.demo.spring.listener;

import com.heqing.demo.spring.config.SpringCoreConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = SpringCoreConfig.class
)
public class ListenerTest {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    public void testListener() {
        applicationEventPublisher.publishEvent(new MyEvent(this, "写作业"));
    }

}

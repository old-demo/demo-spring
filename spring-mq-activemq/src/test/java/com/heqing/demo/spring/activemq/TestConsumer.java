package com.heqing.demo.spring.activemq;

import java.io.IOException;

import com.heqing.demo.spring.activemq.config.SpringCoreConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = SpringCoreConfig.class
)
public class TestConsumer {

    @Test
    public void testConsumer() throws IOException{
        //等待键盘输入 ，保持处于监听状态
        System.in.read();
    }

}

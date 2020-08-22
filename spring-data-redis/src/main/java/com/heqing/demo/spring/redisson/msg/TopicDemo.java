package com.heqing.demo.spring.redisson.msg;

import org.redisson.api.listener.MessageListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TopicDemo implements MessageListener {

    @Async
    @Override
    public void onMessage(CharSequence channel, Object msg) {
        System.out.println("--> 收到了："+msg);
    }

}

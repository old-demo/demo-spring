package com.heqing.demo.spring.redisson.msg;

import org.redisson.api.listener.PatternMessageListener;
import org.springframework.stereotype.Component;

@Component
public class PatternTopicDemo implements PatternMessageListener {

    @Override
    public void onMessage(CharSequence pattern, CharSequence channel, Object msg) {
        System.out.println("模糊话题--> 收到了："+msg);
    }
}

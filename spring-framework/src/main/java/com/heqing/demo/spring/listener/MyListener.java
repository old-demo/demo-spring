package com.heqing.demo.spring.listener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MyListener {

    @Async
    @EventListener
    public void MyEventDone(MyEvent myEvent){
        String msg = myEvent.getMsg();
        System.out.println("好的，我知道你已完成："+msg);
    }
}

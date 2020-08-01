package com.heqing.demo.spring.aop;

import org.springframework.stereotype.Service;

@Service
public class DemoService {

    public void order() {
        System.out.println("测试aop执行顺序");
    }
}

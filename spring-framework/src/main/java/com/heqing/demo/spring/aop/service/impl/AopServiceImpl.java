package com.heqing.demo.spring.aop.service.impl;

import com.heqing.demo.spring.aop.service.AopService;
import org.springframework.stereotype.Service;

@Service
public class AopServiceImpl implements AopService {

    @Override
    public String testReture() {
        return "Hello World";
    }

    @Override
    public String testParameter(int index, String value) {
        return "恭喜，测试成功！";
    }

    @Override
    public void testEx() {
        int num = 1/0;
    }
}

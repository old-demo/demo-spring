package com.heqing.demo.spring.core.service.impl;

import com.heqing.demo.spring.core.service.BeanService;
import org.springframework.stereotype.Service;

@Service("beanServiceNewImpl")
public class BeanServiceNewImpl implements BeanService {

    @Override
    public void sayHello() {
        System.out.println("--> hello New World !");
    }
}

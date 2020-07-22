package com.heqing.demo.spring.core.service.impl;

import com.heqing.demo.spring.core.service.BeanService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("beanServiceImpl")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BeanServiceImpl implements BeanService {

    @Override
    public void sayHello() {
        System.out.println("--> hello World !");
    }

}

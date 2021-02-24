package com.heqing.demo.spring.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.heqing.demo.spring.dubbo.service.DemoProducerService;
import org.springframework.stereotype.Component;

@Component
@Service(version = "1.0.0", timeout = 6000, interfaceClass = DemoProducerService.class)
public class DemoProducerServiceImpl implements DemoProducerService {

    @Override
    public void test() {
        System.out.println("----- 测试 -----");
    }
}

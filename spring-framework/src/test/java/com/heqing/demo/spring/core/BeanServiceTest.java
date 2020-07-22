package com.heqing.demo.spring.core;

import com.heqing.demo.spring.config.SpringCoreConfig;
import com.heqing.demo.spring.core.service.BeanService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = SpringCoreConfig.class
)
public class BeanServiceTest {

    @Autowired
    BeanService beanServiceImpl;

    @Autowired
    BeanService beanServiceNewImpl;

    @Test
    public void testAutoBean() {
        // 使用SpringJUnit4ClassRunner在测试开始时自动创建Spring应用的上下文
        System.out.println("----- 测试 自动化装配bean -----");
        beanServiceImpl.sayHello();
        beanServiceNewImpl.sayHello();
    }

    @Test
    public void testJavaBean() {
        System.out.println("----- 测试 通过Java代码装配bean -----");
        // 需要在启动类上增加 @ComponentScan
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringCoreConfig.class);
        BeanService beanService = context.getBean("beanServiceImpl", BeanService.class);
        beanService.sayHello();

        beanService = context.getBean("beanServiceNewImpl", BeanService.class);
        beanService.sayHello();
    }
}

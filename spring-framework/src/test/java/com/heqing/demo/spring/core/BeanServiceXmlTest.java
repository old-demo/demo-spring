package com.heqing.demo.spring.core;

import com.heqing.demo.spring.core.service.BeanService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath*:spring-core.xml"}
)
public class BeanServiceXmlTest {

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
    public void testXmlBean() {
        System.out.println("----- 测试 通过xml文件装配bean -----");
        // 需要在对应路径的xml中增加 <context:component-scan base-package="com.heqing.demo.*" />
        ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/spring-core.xml");
        BeanService beanService = context.getBean("beanServiceImpl", BeanService.class);
        beanService.sayHello();

        beanService = context.getBean("beanServiceNewImpl", BeanService.class);
        beanService.sayHello();
    }
}

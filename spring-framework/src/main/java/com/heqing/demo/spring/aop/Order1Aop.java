package com.heqing.demo.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class Order1Aop {

    @Pointcut("execution(public * com.heqing.demo.spring.aop.DemoService.*(..))")
    public void order(){}

    @Before("order()")
    public void before() {
        System.out.println("----第一个切面----");
    }

}

package com.heqing.demo.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;

@Aspect
@Component
public class SpringAop {

    /**
     * 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
     */
    @Pointcut("execution(public * com.heqing.demo.spring.aop.service..*(..))")
    public void service(){}

    /**
     * 在核心业务执行前执行，不能阻止核心业务的调用。 时接受JoinPoint切入点对象,可以没有该参数
     */
    @Before("service()")
    public void before() {
        System.out.println("Before - 前置通知");
    }

    /**
     * 核心业务逻辑退出后（包括正常执行结束和异常退出），执行此Advice
     */
    @After("service()")
    public void after() {
        System.out.println("After - 后置通知");
    }

    /**
     * 核心业务逻辑调用正常退出后，不管是否有返回值，正常退出后，均执行此Advice
     */
    @AfterReturning("service()")
    public void afterReturning(){
        System.out.println("AfterReturning - 正常退出通知");
    }

    /**
     * 核心业务逻辑调用异常退出后，执行此Advice，处理错误信息
     * 注意：执行顺序在Around Advice之后
     * @param ex
     */
    @AfterThrowing(pointcut="service()", throwing="ex")
    public void afterThrowing(Exception ex){
        System.out.println("AfterThrowing - 异常通知 :"+ex.getMessage());
    }

    /**
     * 手动控制调用核心业务逻辑，以及调用前和调用后的处理,
            * 注意：当核心业务抛异常后，立即退出，转向AfterAdvice
     * 执行完AfterAdvice，再转到ThrowingAdvice
     * @param pjp
     * @return
             * @throws Throwable
     */
    @Around("service()")
    public Object around (ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("Around - 进入环绕通知");
        long start = System.currentTimeMillis();
        Object obj = parsingJoinPoint(pjp);
        long end = System.currentTimeMillis();
        System.out.println("Around - 退出环绕通知， 该方法一共执行（"+(end-start)+"）ms");
        return obj;
    }

    private static Object parsingJoinPoint(ProceedingJoinPoint point) throws Throwable{
        // 当前访问用户的IP地址
        String ip = InetAddress.getLocalHost().getHostAddress();
        // 拦截的实体类
        Object target = point.getTarget();
        // 拦截的方法
        Signature method = point.getSignature();
        // 拦截的方法参数类型
        Class[] parameTypes = ((MethodSignature)method).getMethod().getParameterTypes();
        // 拦截的方法参数
        Object[] parames = point.getArgs();
        // 拦截的方法返回类型
        Class returnType = ((MethodSignature) method).getReturnType();
        // 错误信息
        String errorInfo = "";
        // 继续执行拦截的方法，屏蔽则不会执行。
        Object returnValue = null;
        try {
            returnValue = point.proceed();
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            errorInfo = sw.toString().substring(0, 1000);
        }

        System.out.println("*************************************************");
        System.out.println("请求ip地址：" + ip);
        System.out.println("请求类名：" + target.getClass());
        System.out.println("请求方法名：" + method.getName());
        if(parameTypes.length > 0) {
            String parameTypeString = "";
            for(Class parameType : parameTypes) {
                parameTypeString += parameType + ", ";
            }
            System.out.println("入参类型：" + parameTypeString.substring(0, parameTypeString.length()-2));
        }
        if(parames.length > 0) {
            String parameString = "";
            for(Object parame : parames) {
                parameString += parame + ", ";
            }
            System.out.println("入参：" + parameString.substring(0, parameString.length()-2));
        }
        System.out.println("出参类型：" + returnType);
        if(returnValue != null ) {
            System.out.println("出参：" + returnValue);
        }
        if(!"".equals(errorInfo)) {
            System.out.println("异常信息：" + errorInfo);
        }
        System.out.println("*************************************************");

        return returnValue;
    }

}

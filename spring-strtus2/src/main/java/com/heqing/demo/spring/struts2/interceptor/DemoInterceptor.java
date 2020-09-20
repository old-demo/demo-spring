package com.heqing.demo.spring.struts2.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.struts2.controller.DemoAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DemoInterceptor extends AbstractInterceptor {

    /** 日志控制. */
    private final Log log = LogFactory.getLog(getClass());

    @Override
    @SuppressWarnings("unchecked")
    public String intercept(ActionInvocation invocation) throws Exception {
        String result = null;
        // 获得当前方法名.
        String methodName = invocation.getInvocationContext().getName();
        try {
            DemoAction action = (DemoAction) invocation.getAction();
            System.out.println("--> action:"+ JSONObject.toJSONString(action));
            //调用被拦截的方法
            result = invocation.invoke();
            System.out.println("--> methodName:"+methodName+", result:"+result);
        } catch (Exception e) {
            log.error("异常类名:" + invocation.getAction().getClass());
            log.error("异常方法:" + methodName, e);
            throw e;
        }
        return result;
    }
}

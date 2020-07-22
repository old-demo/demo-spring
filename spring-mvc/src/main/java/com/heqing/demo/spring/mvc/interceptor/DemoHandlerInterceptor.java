package com.heqing.demo.spring.mvc.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.mvc.util.WebUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

//直接在类名称的上端写入即可，value中指定要引入的拦截器的名称即可
public class DemoHandlerInterceptor implements HandlerInterceptor {

	/** 
     * 在业务处理器处理请求之前被调用（在进入具体的Controller方法之前执行）
     * 这个方法体实现里可以做权限校验，以及其他公共检测（比如 安全校验等）。
     * 如果返回false 
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 如果返回true 
     *    执行下一个拦截器,直到所有的拦截器都执行完毕 
     *    再执行被拦截的Controller 
     *    然后进入拦截器链, 
     *    从最后一个拦截器往回执行所有的postHandle() 
     *    接着再从最后一个拦截器往回执行所有的afterCompletion() 
     */  
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("------------1  业务处理器处理之前------------");

        System.out.println("来访者的IP地址：" + WebUtil.getIpAddr(request));
        System.out.println("请求的URL地址：" + request.getRequestURL().toString());
        System.out.println("请求的资源：" + request.getRequestURI());
        System.out.println("请求的URL地址中附带的参数：" + request.getQueryString());
        System.out.println("请求方式：" + request.getMethod());
        System.out.println("请求方法：" + handler);
        System.out.println("请求参数：" + JSONObject.toJSONString(request.getParameterMap()));

        StringBuffer data = new StringBuffer();
        String line = null;
        BufferedReader reader = request.getReader();
        while (null != (line = reader.readLine())) {
            data.append(line);
        }
        System.out.println("请求参数：" + data.toString());

        System.out.println("--------------------------------------------------");
        return true;
    }
 
    /**
     * 在具体Controller方法方法执行完成后，视图渲染之前执行。
     * 主要用途是在视图渲染前做一些通用的准备工作，可以把一些通用的数据放到第四个参数ModelAndView对象里，供视图渲染时使用。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    	System.out.println("------------2  业务处理器处理之前，生成视图之前------------");
        System.out.println("请求方法：" + handler);
        System.out.println("视图模型：" + JSONObject.toJSONString(modelAndView));
    }
    
    /** 
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等   
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion() 
     */  
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("------------3  完全处理完之后------------");

        System.out.println("请求方法：" + handler);

    }

}

package com.heqing.demo.spring.mvc.interceptor;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

public class DemoWebRequestInterceptor implements WebRequestInterceptor {
    
  /**
   * 同样是在调用Controller方法之前执行。但这个方法没有返回值，这是与HandlerInterceptor 接口的最大区别。
   * 没有返回值，同时也没有HttpServletResponse参数，说明实现该方法无法实现对请求的拦截并返回页面，
   * 其作用仅仅局限于在进入Controller方法之前做一些通用的准备工作，比如放入一些通用的参数到WebRequest对象中，
   * 方便Controller方法执行时使用。
   */  
  @Override  
  public void preHandle(WebRequest request) throws Exception {
      // TODO Auto-generated method stub  
	  System.out.println("------------1  WebRequestInterceptor  preHandle------------");
//      request.setAttribute("request", "request", WebRequest.SCOPE_REQUEST);//这个是放到request范围内的，所以只能在当前请求中的request中获取到  
//      request.setAttribute("session", "session", WebRequest.SCOPE_SESSION);//这个是放到session范围内的，如果环境允许的话它只能在局部的隔离的会话中访问，否则就是在普通的当前会话中可以访问  
//      request.setAttribute("globalSession", "globalSession", WebRequest.SCOPE_GLOBAL_SESSION);//如果环境允许的话，它能在全局共享的会话中访问，否则就是在普通的当前会话中访问  
  }  

  /** 
   * 该方法将在Controller执行之后，返回视图之前执行，ModelMap表示请求Controller处理之后返回的Model对象，所以可以在 
   * 这个方法中修改ModelMap的属性，从而达到改变返回的模型的效果。 
   */  
  @Override  
  public void postHandle(WebRequest request, ModelMap map) throws Exception {
      // TODO Auto-generated method stub  
      System.out.println("------------2  WebRequestInterceptor  postHandle------------");
//      for(String key:map.keySet()) System.out.println("-->key="+key);
  }  

  /** 
   * 该方法将在整个请求完成之后，也就是说在视图渲染之后进行调用，主要用于进行一些资源的释放 
   */  
  @Override  
  public void afterCompletion(WebRequest request, Exception exception) throws Exception {
      // TODO Auto-generated method stub  
	  System.out.println("------------3  WebRequestInterceptor  afterCompletion------------");
  }  
    
}  
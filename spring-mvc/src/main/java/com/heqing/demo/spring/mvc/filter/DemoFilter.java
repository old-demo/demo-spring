package com.heqing.demo.spring.mvc.filter;

import javax.servlet.*;
import java.io.IOException;

public class DemoFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("------------1  过滤器 初始化------------");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("------------2  过滤器 执行------------");
        request.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("------------3  过滤器销毁------------");
    }
}

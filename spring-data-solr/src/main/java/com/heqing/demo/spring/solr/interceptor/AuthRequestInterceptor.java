package com.heqing.demo.spring.solr.interceptor;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.ContextAwareAuthScheme;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class AuthRequestInterceptor implements HttpRequestInterceptor {

    // 对于当前的Solr服务器认证的机制使用的是HttpBase模式完成的
    private ContextAwareAuthScheme authScheme = new BasicScheme() ;

    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        // 根据HTTP上下文获取当前目标服务器的认证处理对象
        AuthState authState = (AuthState) httpContext.getAttribute(HttpClientContext.TARGET_AUTH_STATE);
        // 随后需要考虑当前的状态是否存在
        if (authState != null && authState.getAuthScheme() == null) {   // 现在没有具体的认证出合理模式
            CredentialsProvider credentialsProvider = (CredentialsProvider) httpContext.getAttribute(HttpClientContext.CREDS_PROVIDER) ; // 获取认证提供者
            HttpHost targetHost = (HttpHost) httpContext.getAttribute(HttpClientContext.HTTP_TARGET_HOST) ;// 获取目标主机
            // 根据访问的目标主机，通过认证提供者对象创建一个具体的认证信息
            Credentials credentials = credentialsProvider.getCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()));
            if (credentials == null) {  // 没有提供认证处理
                throw new HttpException("【"+targetHost.getHostName()+"】没有HTTP认证处理支持！") ;
            }
            httpRequest.addHeader(authScheme.authenticate(credentials,httpRequest,httpContext));
        }
    }
}

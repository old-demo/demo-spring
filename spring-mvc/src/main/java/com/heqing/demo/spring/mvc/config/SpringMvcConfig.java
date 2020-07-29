package com.heqing.demo.spring.mvc.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.heqing.demo.spring.mvc.interceptor.DemoHandlerInterceptor;
import com.heqing.demo.spring.mvc.interceptor.DemoWebRequestInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author heqing
 */
@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan("com.heqing.demo.spring.mvc.controller")
public class SpringMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // 网页地址添加 前缀、后缀
        registry.jsp("/WEB-INF/view/", ".jsp");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 静态资源地址映射
        registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // JSON数据处理 高效
        FastJsonHttpMessageConverter fjc = new FastJsonHttpMessageConverter();

        // 配置Fastjson支持
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        fjc.setSupportedMediaTypes(supportedMediaTypes);

        FastJsonConfig fj = new FastJsonConfig();
        // 字符类型字段如果为null,输出为"",而非null
        fj.setSerializerFeatures(SerializerFeature.WriteNullStringAsEmpty);
        // 数值字段如果为null,输出为0,而非null
        fj.setSerializerFeatures(SerializerFeature.WriteNullNumberAsZero);
        // Boolean字段如果为null,输出为false,而非null
        fj.setSerializerFeatures(SerializerFeature.WriteNullBooleanAsFalse);
        // List字段如果为null,输出为[],而非null
        fj.setSerializerFeatures(SerializerFeature.WriteNullListAsEmpty);
        // Map是否输出值为null的字段,默认为false
        fj.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
        // 消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
        fj.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);
        // 全局修改日期格式,默认为false。
        fj.setSerializerFeatures(SerializerFeature.WriteDateUseDateFormat);
        fjc.setFastJsonConfig(fj);

        converters.add(fjc);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 请求拦截器
//        registry.addInterceptor(new DemoHandlerInterceptor()).addPathPatterns("/**");
//        registry.addWebRequestInterceptor(new DemoWebRequestInterceptor()).addPathPatterns("/**");
        // 配置国际化资源
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        // 主要用于获取请求中的locale信息，将其转为Locale对像，获取LocaleResolver对象
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        // 表示语言配置文件是以language开头的文件（示例language_zh_CN.properties）
        messageSource.setBasename("classpath:language");
        // 语言区域里没有找到对应的国际化文件时，默认使用language.properties文件
        messageSource.setUseCodeAsDefaultMessage(true);
        // 默认utf-8编码
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean(name="localeResolver")
    public SessionLocaleResolver sessionLocaleResolver() {
        // 配置SessionLocaleResolver用于将Locale对象存储于Session中供后续使用
        return new SessionLocaleResolver();
    }

    @Bean(name="multipartResolver")
    public CommonsMultipartResolver commonsMultipartResolver() {
        // 文件上传配置
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        commonsMultipartResolver.setMaxUploadSize(5242880);
        commonsMultipartResolver.setMaxInMemorySize(40960);
        commonsMultipartResolver.setResolveLazily(true);
        return commonsMultipartResolver;
    }

    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
          // 设置统一错误处理要跳转的视图
          SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
          Properties properties = new Properties();
          properties.getProperty("java.lang.Exception", "error");
          simpleMappingExceptionResolver.setExceptionMappings(properties);
          return simpleMappingExceptionResolver;
    }
}

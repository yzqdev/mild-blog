package com.site.blog.config;

import com.site.blog.config.interceptor.AdminUserInterceptor;
import com.site.blog.config.interceptor.CorsInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


/**
 * @author yanni
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    CorsInterceptor corsInterceptor;

    @Value("${myblog.upload.file-url}")
    private String fileUrl;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加一个拦截器，拦截以/admin为前缀的url路径
        registry.addInterceptor(corsInterceptor).addPathPatterns("/**");
        registry.addInterceptor(new AdminUserInterceptor()).addPathPatterns("/v2/admin/**").excludePathPatterns("/v2/admin/login").excludePathPatterns("/v2/admin/reg");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" +fileUrl);
    }
}
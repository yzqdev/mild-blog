package com.site.blog.config;

import com.site.blog.config.interceptor.AdminUserInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author yanni
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {


    @Value("${myblog.upload.file-url}")
    private String fileUrl;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加一个拦截器，拦截以/admin为前缀的url路径
        registry.addInterceptor(new AdminUserInterceptor()).addPathPatterns("/v2/admin/**")
                .excludePathPatterns("/v2/auth/**").excludePathPatterns("/v2/client/**");

    }



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" +fileUrl);
    }
}

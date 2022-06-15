package com.site.blog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.site.blog.config.interceptor.AdminUserInterceptor;
import com.site.blog.config.interceptor.CorsInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * @author yanni
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

@Resource
    CorsInterceptor corsInterceptor;
    @Value("${myblog.upload.file-url}")
    private String fileUrl;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor).addPathPatterns("/**");
        // 添加一个拦截器，拦截以/admin为前缀的url路径
        registry.addInterceptor(new AdminUserInterceptor()).addPathPatterns("/v2/admin/**").excludePathPatterns("/v2/admin/login").excludePathPatterns("/v2/admin/reg");
    }



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" +fileUrl);
    }
}

package com.site.blog.config;

import com.site.blog.interceptor.AdminUserInterceptor;
import com.site.blog.interceptor.InterceptorCORS;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


/**
 * @author yanni
 */
@Configuration
public class MyBlogWebMvcConfigurer implements WebMvcConfigurer {
@Resource
    InterceptorCORS interceptorCORS;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加一个拦截器，拦截以/admin为前缀的url路径
registry.addInterceptor(interceptorCORS).addPathPatterns("/**");
        registry.addInterceptor(new AdminUserInterceptor()).addPathPatterns("/v2/admin/**").excludePathPatterns("/v2/admin/login").excludePathPatterns("/v2/admin/reg");
    }

    /**
     * @Description: 重写addResourceHandlers映射文件路径
     * @Param: [registry]
     * @return: void
     * @date: 2019/8/7 9:06
     */
    //@Override
    //public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //    registry.addResourceHandler("/authorImg/**").addResourceLocations("file:" + UploadConstants.UPLOAD_AUTHOR_IMG);
    //    registry.addResourceHandler("/upload/**").addResourceLocations("file:" + UploadConstants.FILE_UPLOAD_DIC);
    //}
}

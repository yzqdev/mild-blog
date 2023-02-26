package com.site.blog.config

import com.site.blog.config.interceptor.AdminUserInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * @author yanni
 */
@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {
    @Value("\${myblog.upload.file-url}")
    private val fileUrl: String? = null
    override fun addInterceptors(registry: InterceptorRegistry) {
        // 添加一个拦截器，拦截以/admin为前缀的url路径
        registry.addInterceptor(AdminUserInterceptor()).addPathPatterns("/v2/admin/**")
            .excludePathPatterns("/v2/auth/**").excludePathPatterns("/v2/client/**")
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {

        registry.addResourceHandler("/upload/**").addResourceLocations("file:$fileUrl")
        registry.addResourceHandler("/**").addResourceLocations("classpath:dist")
    }
}
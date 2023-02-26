package com.site.blog.config.interceptor

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

/**
 * @Author: Yangzhengqian
 * @Description:
 * @Date:Created time 2020/8/7 17:07
 * @Modified By:
 */
@Component
@Slf4j
class CorsInterceptor : HandlerInterceptor {
    val log = LoggerFactory.getLogger(this.javaClass)
    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
       log.info(request.getHeader("origin"))
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"))
        //response.setHeader("Access-Control-Allow-Origin", "http://192.168.72.132:2801" );
        response.setHeader("Access-Control-Allow-Methods", "*")
        response.setHeader("Access-Control-Max-Age", "3600")
        response.setHeader("Access-Control-Allow-Credentials", "true")
        response.setHeader(
            "Access-Control-Allow-Headers",
            "Origin, X-Requested-With, token, Content-Type, Accept, version"
        )
        //浏览器会先发送一个试探请求OPTIONS,然后才会发送真正的请求，为了避免拦截器拦截两次请求，所以不能让OPTIONS请求通过
        if (request.method == HttpMethod.OPTIONS.name()) {
            response.status = HttpServletResponse.SC_OK
            return false
        }
        return true
    }

    @Throws(Exception::class)
    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
    }

    @Throws(Exception::class)
    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
    }
}
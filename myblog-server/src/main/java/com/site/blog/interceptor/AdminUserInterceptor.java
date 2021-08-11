package com.site.blog.interceptor;

import com.site.blog.model.entity.AdminUser;
import com.site.blog.service.AdminUserService;
import com.site.blog.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * @author Yangzhengqian
 * @description
 * @date:Created time 2021/8/11 9:59
 * @modified By:
 */
@Slf4j
@Component
public class AdminUserInterceptor implements HandlerInterceptor {
    @Resource
    AdminUserService adminUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Enumeration<String> headNames = request.getHeaderNames();

        while(headNames.hasMoreElements()){
            String headName = headNames.nextElement();

            System.out.println(headName+":"+request.getHeader(headName));

        }
        String token = request.getHeader("token");
        System.out.println("to------------------------");
      log.info("token="+token);
        boolean flag = JwtUtil.verifyToken(token);
        if (flag) {
            AdminUser user = adminUserService.getById(JwtUtil.getUserId(token));
            request.setAttribute("adminUser", user);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

package com.site.blog.config.interceptor;

import com.site.blog.constants.BaseConstants;
import com.site.blog.model.entity.AdminUser;
import com.site.blog.service.AdminUserService;
import com.site.blog.util.JwtUtil;
import com.site.blog.util.UserUtil;
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


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Enumeration<String> headNames = request.getHeaderNames();

        while (headNames.hasMoreElements()) {
            String headName = headNames.nextElement();

            //System.out.println(headName + ":" + request.getHeader(headName));

        }
        String token = request.getHeader(BaseConstants.TOKEN);
        if (token == null) {
            token = request.getParameter(BaseConstants.TOKEN);
        }

        log.info("token=" + token);
        boolean flag = JwtUtil.verifyToken(token);
        String userId = JwtUtil.getUserId(token);
        if (flag) {

            AdminUser user = UserUtil.getUserByUserCode( userId );
            request.setAttribute(BaseConstants.USER_ATTR, user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

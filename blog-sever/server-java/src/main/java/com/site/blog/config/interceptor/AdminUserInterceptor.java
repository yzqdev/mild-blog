package com.site.blog.config.interceptor;

import com.site.blog.constants.BaseConstants;
import com.site.blog.model.entity.AdminUser;
import com.site.blog.model.vo.UserVo;
import com.site.blog.util.JwtService;
import com.site.blog.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        boolean flag = JwtService.verifyToken(token);
        String userId = JwtService.getUserId(token);
        if (flag) {

            AdminUser user = UserUtil.getUserByUserCode( userId );
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user,userVo);
            log.info("这里设置用户到session");
            log.info("user={}","myid");
            log.info("user=" + user);
            request.setAttribute(BaseConstants.USER_ATTR, userVo);
        }else{
            log.info("token验证失败");
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

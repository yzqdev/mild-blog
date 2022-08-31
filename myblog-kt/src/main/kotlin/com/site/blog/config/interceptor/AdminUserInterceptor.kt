package com.site.blog.config.interceptor

import com.site.blog.constants.BaseConstants
import com.site.blog.model.vo.UserVo
import com.site.blog.util.JwtUtil
import com.site.blog.util.UserUtil
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author Yangzhengqian
 * @description
 * @date:Created time 2021/8/11 9:59
 * @modified By:
 */

@Component
class AdminUserInterceptor : HandlerInterceptor {
    val log = LoggerFactory.getLogger(this.javaClass)
    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val headNames = request.headerNames
        while (headNames.hasMoreElements()) {
            val headName = headNames.nextElement()

            //System.out.println(headName + ":" + request.getHeader(headName));
        }
        var token = request.getHeader(BaseConstants.TOKEN)
        if (token == null) {
            token = request.getParameter(BaseConstants.TOKEN)
        }
        log.info("token=$token")
        val flag = JwtUtil.verifyToken(token)
        val userId = JwtUtil.getUserId(token)
        if (flag) {
            val user = UserUtil.getUserByUserCode(userId)
            val userVo = UserVo()
            if (user != null) {
                BeanUtils.copyProperties(user, userVo)
            }
            log.info("这里设置用户到session")
            log.info("user={}", "myid")
            log.info("user=$user")
            request.setAttribute(BaseConstants.USER_ATTR, userVo)
        } else {
            log.info("token验证失败")
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
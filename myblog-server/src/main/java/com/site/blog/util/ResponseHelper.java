package com.site.blog.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;


/**
 * @author: dobell
 * @link: http://www.dobell.me/
 * @description:
 */
@Slf4j
public class ResponseHelper {


    public static HttpServletResponse getHttpServletResponse() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }


    public static void response(String result, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            response.setCharacterEncoding("utf-8");
            out.print(result);
            out.flush();
        } catch (Exception e) {
        } finally {
            out.close();
        }
    }

    public static void response(String result) {
        response(result, getHttpServletResponse());
    }

    /**
     * 非法url返回身份错误信息
     */


    public static void sendRedirect(HttpServletResponse httpServletResponse, String url) throws IOException {
        try {
            log.info("正在跳转路径:" + url);
            httpServletResponse.sendRedirect(url);
        } catch (Exception e) {
            e.printStackTrace();
            httpServletResponse.sendError(500, e.getMessage());
        }
    }

}


package com.site.blog.util;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Spring切面工具类
 *
 * @author xuyuxiang
 * @date 2020/3/16 17:56
 */
public class JoinPointUtil {

    /**
     * 获取切面的参数json
     *
     * @author xuyuxiang
     * @date 2020/3/16 17:57
     */
    public static String getArgsJsonString(JoinPoint joinPoint) {
        StringBuilder argsJson = new StringBuilder();
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (!isFilterObject(arg)) {
                Optional.ofNullable(arg).ifPresent((i) -> {
                    String jsonStr = JSONUtil.toJsonStr(i);
                    argsJson.append(jsonStr).append(" ");
                });

            }
        }
        return argsJson.toString().trim();
    }

    /**
     * 判断是否需要拼接参数，过滤掉HttpServletRequest,MultipartFile,HttpServletResponse等类型参数
     *
     * @author xuyuxiang
     * @date 2020/3/16 17:59
     */
    private static boolean isFilterObject(Object arg) {
        return arg instanceof MultipartFile || arg instanceof HttpServletRequest || arg instanceof HttpServletResponse;
    }

}

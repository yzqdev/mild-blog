package com.site.blog.util

import cn.hutool.json.JSONUtil
import org.aspectj.lang.JoinPoint
import org.springframework.web.multipart.MultipartFile
import java.util.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

/**
 * Spring切面工具类
 *
 * @author xuyuxiang
 * @date 2020/3/16 17:56
 */
object JoinPointUtil {
    /**
     * 获取切面的参数json
     *
     * @author xuyuxiang
     * @date 2020/3/16 17:57
     */
    @JvmStatic
    fun getArgsJsonString(joinPoint: JoinPoint): String {
        val argsJson = StringBuilder()
        val args = joinPoint.args
        for (arg in args) {
            if (!isFilterObject(arg)) {
                Optional.ofNullable(arg).ifPresent { i: Any? ->
                    val jsonStr = JSONUtil.toJsonStr(i)
                    argsJson.append(jsonStr).append(" ")
                }
            }
        }
        return argsJson.toString().trim { it <= ' ' }
    }

    /**
     * 判断是否需要拼接参数，过滤掉HttpServletRequest,MultipartFile,HttpServletResponse等类型参数
     *
     * @author xuyuxiang
     * @date 2020/3/16 17:59
     */
    private fun isFilterObject(arg: Any): Boolean {
        return arg is MultipartFile || arg is HttpServletRequest || arg is HttpServletResponse
    }
}
package com.site.blog.util

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.io.IOException
import java.util.*
import jakarta.servlet.http.HttpServletResponse

@Slf4j
object ResponseHelper {
    val log = LoggerFactory.getLogger(this.javaClass)
    val httpServletResponse: HttpServletResponse?
        get() = (Objects.requireNonNull(RequestContextHolder.getRequestAttributes()) as ServletRequestAttributes).response

    @JvmOverloads
    fun response(result: String?, response: HttpServletResponse? = httpServletResponse) {
        try {
            response!!.writer.use { out ->
                response.characterEncoding = "utf-8"
                out.print(result)
                out.flush()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 非法url返回身份错误信息
     */
    @Throws(IOException::class)
    fun sendRedirect(httpServletResponse: HttpServletResponse, url: String) {
        try {
             log.info("正在跳转路径:$url")
            httpServletResponse.sendRedirect(url)
        } catch (e: Exception) {
            e.printStackTrace()
            httpServletResponse.sendError(500, e.message)
        }
    }
}
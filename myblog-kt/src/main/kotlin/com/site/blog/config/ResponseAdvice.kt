package com.site.blog.config

import com.site.blog.constants.HttpStatusEnum
import com.site.blog.exception.ResultException
import com.site.blog.model.dto.Result
import com.site.blog.util.ResultGenerator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

/**
 * @author yanni
 * @date time 2022/6/17 11:44
 * @modified By:
 */
@RestControllerAdvice
class ResponseAdvice {
    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(ResultException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun exceptionHandler(req: HttpServletRequest?, e: ResultException): Result<*> {
        log.error("发生业务异常！原因是：{}", e.message)
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR, false, e.message)
    }

    /**
     * 处理空指针的异常
     * @param req
     * @param e
     * @return
     */
//    @ExceptionHandler(value = [NullPointerException::class])
//    @ResponseBody
//    fun exceptionHandler(req: HttpServletRequest?, e: NullPointerException?): Result<String?> {
//        log.error("发生空指针异常！原因是:", e)
//
//        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR, false, "空指针异常")
//
//    }

    /**
     * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun unknownExceptionHandler(req: HttpServletRequest?, e: Exception): Result<*> {
        log.error("未知异常！原因是:", e)
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR, false, e.message)
    }
}
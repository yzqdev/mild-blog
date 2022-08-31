package com.site.blog.util

import com.site.blog.constants.HttpStatusEnum
import com.site.blog.model.dto.Result

object ResultGenerator {
    @JvmStatic
    fun <T> getResultByHttp(constants: HttpStatusEnum, msg: String?, data: T): Result<T> {
        val result = Result<T>()
        result.resultCode = constants.status
        result.message = msg
        result.data=data
        result.timestamp = System.currentTimeMillis()
        return result
    }
    @JvmStatic
    fun <T> getResultByHttp(constants: HttpStatusEnum, data: T): Result<T> {
        val result = Result<T>()
        result.resultCode = constants.status
        result.message = constants.content
        result.data=data
        result.timestamp = System.currentTimeMillis()
        return result
    }
@JvmStatic
    fun <T> getResultByHttp(constants: HttpStatusEnum, success: Boolean, data: T): Result<T> {
        val result = Result<T>()
        result.resultCode = constants.status
        result.message = constants.content
        result.data=data
        result.success = success
        result.timestamp = System.currentTimeMillis()
        return result
    }

    /**
     * 自定义提示消息
     *
     * @param constants Http枚举
     * @param msg       提示消息
     */
    @JvmStatic
    fun getResultByMsg(constants: HttpStatusEnum, msg: String?): Result<String> {
        val result = Result<String>()
        result.resultCode = constants.status
        result.message = msg
        result.timestamp = System.currentTimeMillis()
        return result
    }

    @JvmStatic
    fun getResultByHttp(constants: HttpStatusEnum): Result<String> {
        val result = Result<String>()
        result.resultCode = constants.status
        result.message = constants.content
        result.timestamp = System.currentTimeMillis()
        return result
    }
}
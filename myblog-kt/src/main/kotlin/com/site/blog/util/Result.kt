package com.site.blog.util

import com.site.blog.constants.HttpStatusEnum
data class ResultDto<T>  (
    var resultCode: Int? = null,
    var message: String? = null,
    var data: T? = null,

    var success: Boolean? = null,
    var timestamp: Long = 0



)
object Result {
    @JvmStatic
    fun <T> getResultByHttp(constants: HttpStatusEnum, msg: String?, data: T): ResultDto<T> {
        val resultDto = ResultDto<T>()
        resultDto.resultCode = constants.status
        resultDto.message = msg
        resultDto.data = data
        resultDto.timestamp = System.currentTimeMillis()
        return resultDto
    }

    @JvmStatic
    fun <T> getResultByHttp(constants: HttpStatusEnum, data: T): ResultDto<T> {
        val resultDto = ResultDto<T>()
        resultDto.resultCode = constants.status
        resultDto.message = constants.content
        resultDto.data = data
        resultDto.timestamp = System.currentTimeMillis()
        return resultDto
    }

    @JvmStatic
    fun <T> getResultByHttp(constants: HttpStatusEnum, success: Boolean, data: T): ResultDto<T> {
        val resultDto = ResultDto<T>()
        resultDto.resultCode = constants.status
        resultDto.message = constants.content
        resultDto.data = data
        resultDto.success = success
        resultDto.timestamp = System.currentTimeMillis()
        return resultDto
    }

    /**
     * 自定义提示消息
     *
     * @param constants Http枚举
     * @param msg       提示消息
     */
    @JvmStatic
    fun getResultByMsg(constants: HttpStatusEnum, msg: String?): ResultDto<String> {
        val resultDto = ResultDto<String>()
        resultDto.resultCode = constants.status
        resultDto.message = msg
        resultDto.timestamp = System.currentTimeMillis()
        return resultDto
    }

    @JvmStatic
    fun getResultByHttp(constants: HttpStatusEnum): ResultDto<String> {
        val resultDto = ResultDto<String>()
        resultDto.resultCode = constants.status
        resultDto.message = constants.content
        resultDto.timestamp = System.currentTimeMillis()
        return resultDto
    }
    @JvmStatic
    fun <T> ok(msg:String) :ResultDto<T>{
        val resultDto=ResultDto<T>().apply {
            resultCode=HttpStatusEnum.OK.status
            message=msg
            data=msg as T
            success=true
            timestamp=System.currentTimeMillis()
        }
        return resultDto
    }
    @JvmStatic
    fun <T> ok(data:T,msg:String) :ResultDto<T>{
        val resultDto=ResultDto<T>().apply {
            resultCode=HttpStatusEnum.OK.status
            message=msg

            success=true
            timestamp=System.currentTimeMillis()
        }
        resultDto.data=data
        return resultDto
    }
    @JvmStatic
    fun <T> err(code:HttpStatusEnum,msg:String):ResultDto<T>{
        val result=ResultDto<T>().apply {
            resultCode=code.status
            data=msg as T
            message=msg
            success=false
            timestamp=System.currentTimeMillis()
        }
        return result
    }
}
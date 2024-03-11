package com.site.blog.model.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.fasterxml.jackson.annotation.JsonFormat
import java.io.Serializable
import java.time.LocalDateTime

/**
 * @author yanni
 * @date time 2022/6/16 2:04
 * @modified By:
 */
data class SysOpLog(
    @TableId(type = IdType.ASSIGN_ID)
    var id: String? = null,
    var name: String? = null,
    var opType: String? = null,
    var message: String? = null,
    var ip: String? = null,
    var location: String? = null,
    var browser: String? = null,
    var os: String? = null,
    var url: String? = null,
    var className: String? = null,
    var methodName: String? = null,
    var reqMethod: String? = null,
    var param: String? = null,
    var result: String? = null,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var opTime: LocalDateTime? = null,
    var account: String? = null,
){
    override fun toString(): String {
        return "SysOpLog(id=$id, name=$name, opType=$opType, message=$message, ip=$ip, location=$location, browser=$browser, os=$os, url=$url, className=$className, methodName=$methodName, reqMethod=$reqMethod, param=$param, result=$result, opTime=$opTime, account=$account)"
    }
}



package com.site.blog.model.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

/**
 * @author yanni
 * @date time 2022/6/17 12:33
 * @modified By:
 */
data class SysDictData (
    @TableId(type = IdType.ASSIGN_ID)
    var id: String? = null,
    var typeId: String? = null,
    var value: String? = null,
    var code: String? = null,
    var sort: Int? = null,
    var remark: String? = null,
    var status: Boolean? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var createTime: LocalDateTime? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var updateTime: LocalDateTime? = null,
)
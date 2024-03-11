package com.site.blog.model.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

/**
 * @author yanni
 * @date time 2022/6/17 15:47
 * @modified By:
 */
data class SysTimer(
    @TableId(type = IdType.ASSIGN_ID)
    var id: String? = null,
    var timerName: String? = null,
    var actionClass: String? = null,
    var cron: String? = null,
    var status: Boolean? = null,
    var remark: String? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var createTime: LocalDateTime? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var updateTime: LocalDateTime? = null,
)
  
 
package com.site.blog.model.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId

/**
 * @author yanni
 * @date time 2022/6/18 1:23
 * @modified By:
 */
data class EmailConfig(
    @TableId(type = IdType.ASSIGN_ID)
    var id: String? = null,
    var email: String? = null,

    /**
     * 电子邮件的关键
     */
    var emailKey: String? = null,

    /**
     * 电子邮件网址
     */
    var emailUrl: String? = null,

    /**
     * 端口
     */
    var port: String? = null,
    var emailName: String? = null,
    var enable: Boolean? = null,
)
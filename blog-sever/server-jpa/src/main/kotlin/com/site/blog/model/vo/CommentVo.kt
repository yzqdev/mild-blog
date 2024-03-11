package com.site.blog.model.vo

import com.fasterxml.jackson.annotation.JsonFormat
import com.site.blog.model.entity.BlogInfo
import java.time.LocalDateTime

/**
 * @author yanni
 * @date time 2022/6/15 11:45
 * @modified By:
 */
data class CommentVo (
    var  id: String? = null,
    var  blogId: String? = null,
    var  blogInfo: BlogInfo? = null,

    /**
     * 评论者名称
     */
    var  commentator: String? = null,

    /**
     * 评论人的邮箱
     */
    var  email: String? = null,

    /**
     * 网址
     */
    var  websiteUrl: String? = null,

    /**
     * 评论内容
     */
    var  commentBody: String? = null,

    /**
     * 评论创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") //将Date转换成String,一般后台传值给前台时

    var  commentCreateTime: LocalDateTime? = null,

    /**
     * 评论时的ip地址
     */
    var  commentatorIp: String? = null,
    var  userAgent: String? = null,

    /**
     * 回复内容
     */
    var  replyBody: String? = null,

    /**
     * 回复时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var  replyCreateTime: LocalDateTime? = null,
    var  commentStatus: Boolean? = null,
    var  os: String? = null,
)
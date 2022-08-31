package com.site.blog.model.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.fasterxml.jackson.annotation.JsonFormat
import java.io.Serializable
import java.time.LocalDateTime

/**
 *
 *
 * 博客信息表
 *
 *
 */
data class BlogInfo(
    /**
     * 博客表主键id
     */
    @TableId(value = "blog_id", type = IdType.ASSIGN_ID)
    var blogId: String? = null,

    /**
     * 博客标题
     */
    @TableField("blog_title")
    var blogTitle: String? = null,

    /**
     * 博客自定义路径url
     */
    @TableField("sub_url")
    var subUrl: String? = null,

    /**
     * 博客前言
     */
    @TableField("preface")
    var preface: String? = null,

    /**
     * 博客内容
     */
    @TableField("blog_content")
    var blogContent: String? = null,

    /**
     * 阅读量
     */
    @TableField("blog_views")
    var blogViews: Long? = null,

    /**
     * 0-允许评论 1-不允许评论
     */
    @TableField("enable_comment")
    var enableComment: Boolean? = null,

    /**
     * 是否删除 0=草稿 1=发布
     */
    @TableField("show")
    var show: Boolean? = null,

    /**
     * 添加时间,将Date转换成String,一般后台传值给前台时
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("create_time")
    var createTime: LocalDateTime? = null,

    /**
     * 修改时间//将Date转换成String,一般后台传值给前台时
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("update_time")
    var updateTime: LocalDateTime? = null,

    @TableField("deleted")
    var deleted: Boolean? = null,
)




package com.site.blog.model.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class BlogInfoDo (
    /**
     * 博客表主键id
     */
    var  blogId: String? = null,

    /**
     * 博客标题
     */
    var  blogTitle: String? = null,

    /**
     * 博客自定义路径url
     */
    var  subUrl: String? = null,

    /**
     * 博客前言
     */
    var  preface: String? = null,

    /**
     * 博客内容
     */
    var  blogContent: String? = null,

    /**
     * 博客分类id
     */
    var  blogCategoryId: String? = null,

    /**
     * 博客标签ids
     */
    var  blogTagIds: List<String>? = null,

    /**
     * 0-草稿 1-发布
     */
    var  show: Boolean? = null,

    /**
     * 阅读量
     */
    var  blogViews: Long? = null,

    /**
     * 0-允许评论 1-不允许评论
     */
    //
    var  enableComment: Boolean? = null,
    /**
     * 是否删除 0=否 1=是
     */
    //
    //private Integer isDeleted;
    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var  createTime: LocalDateTime? = null,
    )
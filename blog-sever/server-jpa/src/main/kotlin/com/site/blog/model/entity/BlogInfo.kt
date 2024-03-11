package com.site.blog.model.entity


import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.io.Serializable
import java.time.LocalDateTime

/**
 *
 *
 * 博客信息表
 *
 *
 */
@Entity
data class BlogInfo(
    /**
     * 博客表主键id
     */


    /**
     * 博客标题
     */
    var blogTitle: String? = null,

    /**
     * 博客自定义路径url
     */
    var subUrl: String? = null,

    /**
     * 博客前言
     */
    var preface: String? = null,

    /**
     * 博客内容
     */
    var blogContent: String? = null,

    /**
     * 阅读量
     */
    var blogViews: Long? = null,

    /**
     * 0-允许评论 1-不允许评论
     */
    var enableComment: Boolean? = null,

    /**
     * 是否删除 0=草稿 1=发布
     */
    var show: Boolean? = null,

    /**
     * 添加时间,将Date转换成String,一般后台传值给前台时
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var createTime: LocalDateTime? = null,

    /**
     * 修改时间//将Date转换成String,一般后台传值给前台时
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var updateTime: LocalDateTime? = null,

    var deleted: Boolean? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "blog_id", nullable = false)
    open var blogId: Long? = null
}




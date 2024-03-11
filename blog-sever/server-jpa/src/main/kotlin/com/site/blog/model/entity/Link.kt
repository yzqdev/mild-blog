package com.site.blog.model.entity

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.io.Serializable
import java.sql.Timestamp

/**
 *
 *
 * 友情链接表
 *
 *
 */
@Entity
data class Link  (
    /**
     * 友链表主键id
     */


    /**
     * 友链类别 0-友链 1-推荐 2-个人网站
     */
    
    var linkType: Int? = null,

    /**
     * 网站名称
     */
    
    var linkName: String? = null,

    /**
     * 网站链接
     */
    
    var linkUrl: String? = null,

    /**
     * 网站描述
     */
    
    var linkDescription: String? = null,

    /**
     * 用于列表排序
     */
    
    var linkRank: Int? = null,

    /**
     * 是否删除 0-未删除 1-已删除
     */
    
    var show: Boolean? = null,

    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    
    var createTime: Timestamp? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    
    var updateTime: Timestamp? = null,


) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "link_id", nullable = false)
    open var linkId: Long? = null
}
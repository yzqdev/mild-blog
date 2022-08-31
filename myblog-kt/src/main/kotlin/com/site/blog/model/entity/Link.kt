package com.site.blog.model.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.fasterxml.jackson.annotation.JsonFormat
import java.io.Serializable
import java.sql.Timestamp

/**
 *
 *
 * 友情链接表
 *
 *
 */
data class Link  (
    /**
     * 友链表主键id
     */
    @TableId(value = "link_id", type = IdType.ASSIGN_ID)
    var linkId: String? = null,

    /**
     * 友链类别 0-友链 1-推荐 2-个人网站
     */
    @TableField("link_type")
    var linkType: Int? = null,

    /**
     * 网站名称
     */
    @TableField("link_name")
    var linkName: String? = null,

    /**
     * 网站链接
     */
    @TableField("link_url")
    var linkUrl: String? = null,

    /**
     * 网站描述
     */
    @TableField("link_description")
    var linkDescription: String? = null,

    /**
     * 用于列表排序
     */
    @TableField("link_rank")
    var linkRank: Int? = null,

    /**
     * 是否删除 0-未删除 1-已删除
     */
    @TableField("show")
    var show: Boolean? = null,

    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("create_time")
    var createTime: Timestamp? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("update_time")
    var updateTime: Timestamp? = null,


)
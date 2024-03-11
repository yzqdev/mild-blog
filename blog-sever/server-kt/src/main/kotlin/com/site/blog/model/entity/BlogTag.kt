package com.site.blog.model.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import java.io.Serial
import java.io.Serializable
import java.sql.Timestamp

/**
 *
 *
 * 博客跟标签的关系表
 *
 *
 */
data class BlogTag (
    /**
     * 关系表id
     */
    @TableId(value = "relation_id", type = IdType.ASSIGN_ID)
    var  relationId: Long? = null,

    /**
     * 博客id
     */
    @TableField("blog_id")
    var  blogId: String? = null,

    /**
     * 标签id
     */
    @TableField(value = "tag_id")
    var  tagId: String? = "",

    /**
     * 添加时间
     */
    @TableField("create_time")
    var  createTime: Timestamp? = null,

)
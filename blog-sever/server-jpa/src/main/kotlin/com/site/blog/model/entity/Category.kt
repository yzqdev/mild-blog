package com.site.blog.model.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.extension.activerecord.Model
import com.fasterxml.jackson.annotation.JsonFormat
import java.io.Serializable
import java.time.LocalDateTime

/**
 *
 *
 * 博客分类
 *
 *
 */
data class Category(
    /**
     * 分类表主键
     */
    @TableId(value = "category_id", type = IdType.ASSIGN_ID)
    var categoryId: String? = null,

    /**
     * 分类的名称
     */
    
    var categoryName: String? = null,

    /**
     * 分类的图标
     */
    
    var categoryIcon: String? = null,

    /**
     * 分类的排序值 被使用的越多数值越大
     */
    
    var categoryRank: Int? = null,

    /**
     * 是否删除 0=否 1=是
     */
    
    var show: Boolean? = null,

    /**
     * 创建时间
     */
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var createTime: LocalDateTime? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var updateTime: LocalDateTime? = null,
) : Model<Category?>()



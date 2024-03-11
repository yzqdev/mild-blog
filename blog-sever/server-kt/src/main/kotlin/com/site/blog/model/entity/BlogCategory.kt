package com.site.blog.model.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.extension.activerecord.Model
import java.sql.Timestamp

/**
 * @author yzq
 * @description
 * @date:Created time 2021/8/25 23:57
 * @modified By:
 */
data class BlogCategory(
    /**
     * 关系id
     */
    @TableId(type = IdType.ASSIGN_ID)
    var relationId: String? = null,

    /**
     * 博客id
     */
    var blogId: String? = null,

    /**
     * 类别id
     */
    var categoryId: String? = null,

    /**
     * 创建时间
     */
    var createTime: Timestamp? = null,
) : Model<BlogCategory?>()
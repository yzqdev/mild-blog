package com.site.blog.model.entity


import jakarta.persistence.*
import java.sql.Timestamp

/**
 *
 * @author yzq
 * @description
 * @date:Created time 2021/8/25 23:57
 * @modified By:
 */
@Entity
data class BlogCategory(
    /**
     * 关系id
     */


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
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "relation_id", nullable = false)
    open var relationId: Long? = null
}
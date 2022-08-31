package com.site.blog.model.vo

import com.fasterxml.jackson.annotation.JsonFormat
import com.site.blog.model.entity.Category
import com.site.blog.model.entity.Tag
import java.time.LocalDateTime

data class BlogDetailVO  (
    var  blogId: String? = null,
    var  blogTitle: String? = null,
    var  blogCategory: Category? = null,
    var  commentCount: Long? = null,
    var  blogCategoryIcon: String? = null,
    var  preface: String? = null,
    var  blogCoverImage: String? = null,
    var  blogViews: Long? = null,
    var  blogTags: List<Tag?> = emptyList(),
    var  blogContent: String? = null,
    var  enableComment: Boolean? = null,
    var  show: Boolean? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var  createTime: LocalDateTime? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var  updateTime: LocalDateTime? = null,
    var  deleted: Boolean? = null,
)
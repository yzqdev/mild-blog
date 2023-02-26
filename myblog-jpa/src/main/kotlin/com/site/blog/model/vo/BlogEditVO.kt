package com.site.blog.model.vo

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class BlogEditVO  (
    var  blogId: String? = null,
    var  blogTitle: String? = null,
    var  blogCategoryId: String? = null,
    var  commentCount: Long? = null,
    var  blogCategoryIcon: String? = null,
    var  show: Boolean? = null,
    var  subUrl: String? = null,
    var  preface: String? = null,
    var  blogCoverImage: String? = null,
    var  blogViews: Long? = null,
    var  blogTagIds: List<String?> = emptyList(),
    var  blogContent: String? = null,
    var  enableComment: Boolean? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var  createTime: LocalDateTime? = null,
)
package com.site.blog.model.dto

import com.site.blog.model.entity.BlogConfig

data class IndexDto(
    var categoryCount: Int? = 0,
    var blogCount: Int? = 0,
    var linkCount: Int? = 0,
    var tagCount: Int? = 0,
    var commentCount: Int? = 0,
    var sysList: List<BlogConfig>? = null
)
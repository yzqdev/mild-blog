package com.site.blog.model.dto

data class BlogPageCondition (
    var pageNum: Int? = 0,
    var pageSize: Int? = 20,
    var keyword: String? = null,
    var pageName: String? = null,
    var tagId: String? = null,
    var categoryId: String? = null,
)
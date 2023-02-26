package com.site.blog.service


import com.site.blog.model.entity.Category

/**
 *
 *
 * 博客分类 服务类
 *
 *
 */
interface CategoryService  {
    fun clearCategory(id: Long): Boolean
}
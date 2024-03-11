package com.site.blog.service

import com.baomidou.mybatisplus.extension.service.IService
import com.site.blog.model.entity.Category

/**
 *
 *
 * 博客分类 服务类
 *
 *
 */
interface CategoryService : IService<Category?> {
    fun clearCategory(id: String?): Boolean
}
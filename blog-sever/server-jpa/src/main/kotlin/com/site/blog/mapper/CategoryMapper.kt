package com.site.blog.mapper


import com.site.blog.model.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


/**
 *
 *
 * 博客分类 Mapper 接口
 *
 *
 * @author zhulin
 * @since 2019-08-30
 */
@Repository
interface CategoryMapper : JpaRepository<Category,Long>
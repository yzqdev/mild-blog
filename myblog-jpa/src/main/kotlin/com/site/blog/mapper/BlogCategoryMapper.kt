package com.site.blog.mapper


import com.site.blog.model.entity.BlogCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


/**
 * @author yzq
 * @description
 * @date:Created time 2021/8/25 23:59
 * @modified By:
 */
@Repository
interface BlogCategoryMapper : JpaRepository<BlogCategory,Long>
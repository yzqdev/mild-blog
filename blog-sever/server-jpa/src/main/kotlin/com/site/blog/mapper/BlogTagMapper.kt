package com.site.blog.mapper


import com.site.blog.model.entity.BlogTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


/**
 *
 *
 * 博客跟标签的关系表 Mapper 接口
 *
 *
 * @author zhulin
 * @since 2019-08-28
 */
@Repository
interface BlogTagMapper : JpaRepository<BlogTag,Long>
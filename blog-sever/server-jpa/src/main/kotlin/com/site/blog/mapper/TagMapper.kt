package com.site.blog.mapper


import com.site.blog.model.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


/**
 *
 *
 * 标签表 Mapper 接口
 *
 *
 * @author zhulin
 * @since 2019-08-28
 */
@Repository
interface TagMapper : JpaRepository<Tag,Long>
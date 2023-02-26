package com.site.blog.mapper


import com.site.blog.model.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


/**
 *
 *
 * 评论信息表 Mapper 接口
 *
 *
 * @author zhulin
 * @since 2019-09-04
 */
@Repository
interface CommentMapper : JpaRepository<Comment,Long>
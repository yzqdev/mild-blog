package com.site.blog.util

import com.site.blog.model.entity.BlogInfo
import com.site.blog.model.entity.Comment
import com.site.blog.model.vo.BlogDetailVO
import com.site.blog.model.vo.CommentVo
import org.springframework.beans.BeanUtils

object BeanMapUtil {
    @JvmStatic
    fun copyBlog(blogInfo: BlogInfo?): BlogDetailVO {
        val blogDetailVO = BlogDetailVO()
        BeanUtils.copyProperties(blogInfo!!, blogDetailVO)
        return blogDetailVO
    }

    @JvmStatic
    fun copyComment(comment: Comment?): CommentVo {
        val commentVo = CommentVo()
        BeanUtils.copyProperties(comment!!, commentVo)
        return commentVo
    }
}
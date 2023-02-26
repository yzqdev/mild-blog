package com.site.blog.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper
import com.site.blog.constants.ShowEnum
import com.site.blog.mapper.BlogInfoMapper
import com.site.blog.mapper.BlogTagMapper
import com.site.blog.mapper.CommentMapper
import com.site.blog.model.entity.BlogInfo
import com.site.blog.model.entity.BlogTag
import com.site.blog.model.entity.Comment
import com.site.blog.model.vo.SimpleBlogListVO
import com.site.blog.service.BlogInfoService
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 *
 *
 * 博客信息表 服务实现类
 *
 *
 */
@Service
class BlogInfoServiceImpl(
    private val blogInfoMapper: BlogInfoMapper,
    private val blogTagMapper: BlogTagMapper,
    private val commentMapper: CommentMapper
) :  BlogInfoService {
    override fun getNewBlog(): List<SimpleBlogListVO> {
        val simpleBlogListVOS = ArrayList<SimpleBlogListVO>()
        val page = Page<BlogInfo>(1, 5)
        blogInfoMapper.selectPage(
            page, KtQueryWrapper(BlogInfo())

                .eq(BlogInfo::show, ShowEnum.SHOW.status)
                .orderByDesc(BlogInfo::createTime)
        )
        for (blogInfo in page.records) {
            val simpleBlogListVO = SimpleBlogListVO()
            BeanUtils.copyProperties(blogInfo!!, simpleBlogListVO)
            simpleBlogListVOS.add(simpleBlogListVO)
        }
        return simpleBlogListVOS
    }

    override fun getHotBlog(): List<SimpleBlogListVO> {
        val simpleBlogListVOS = ArrayList<SimpleBlogListVO>()
        val page = Page<BlogInfo>(1, 5)
        blogInfoMapper.selectPage(
            page, KtQueryWrapper(BlogInfo())

                .eq(BlogInfo::show, ShowEnum.SHOW.status)
                .orderByDesc(BlogInfo::blogViews)
        )
        for (blogInfo in page.records) {
            val simpleBlogListVO = SimpleBlogListVO()
            BeanUtils.copyProperties(blogInfo!!, simpleBlogListVO)
            simpleBlogListVOS.add(simpleBlogListVO)
        }
        return simpleBlogListVOS
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun clearBlogInfo(blogId: String): Boolean {
        if (SqlHelper.retBool(blogInfoMapper.deleteById(blogId))) {
            val tagRelationWrapper = KtQueryWrapper(BlogTag())
            tagRelationWrapper.eq(BlogTag::blogId, blogId)
            blogTagMapper.delete(tagRelationWrapper)
            val commentWrapper = KtQueryWrapper(Comment())
            commentWrapper.eq(Comment::blogId, blogId)
            commentMapper.delete(commentWrapper)
            return true
        }
        return false
    }

    override fun getViewsSum(): Int {
        val res = blogInfoMapper.getViews()
        return res ?: 0

    }
}
package com.site.blog.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper

import com.site.blog.mapper.BlogTagMapper
import com.site.blog.model.entity.BlogInfo
import com.site.blog.model.entity.BlogTag
import com.site.blog.service.BlogTagService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.function.Function

/**
 *
 *
 * 博客跟标签的关系表 服务实现类
 *
 *
 */
@Service
class BlogTagServiceImpl(private val blogTagMapper:BlogTagMapper) : ServiceImpl<BlogTagMapper, BlogTag>(),
    BlogTagService {
    @Transactional(rollbackFor = [Exception::class])
    override fun removeAndsaveBatch(blogTagIds: List<String>, blogInfo: BlogInfo) {
        val blogId = blogInfo.blogId
        val list: List<BlogTag> = blogTagIds.map { BlogTag(tagId = it, blogId = blogId) }
        blogTagMapper.delete(
            QueryWrapper<BlogTag>()
                .lambda()
                .eq(BlogTag::blogId, blogInfo.blogId)
        )
        for (item in list) {
            blogTagMapper.insert(item)
        }
    }
}
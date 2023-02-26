package com.site.blog.service.impl


import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper

import com.site.blog.constants.ShowEnum
import com.site.blog.constants.SysConfigConstants
import com.site.blog.mapper.TagMapper
import com.site.blog.model.entity.BlogTag
import com.site.blog.model.entity.BlogTagCount
import com.site.blog.model.entity.Tag
import com.site.blog.service.BlogTagService
import com.site.blog.service.TagService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 *
 *
 * 标签表 服务实现类
 *
 *
 */
@Service
class TagServiceImpl(private val blogTagService: BlogTagService, private val tagMapper: TagMapper) :
     TagService {
    override fun getBogTagCountForIndex(): List<BlogTagCount> {
        val queryWrapper =
            KtQueryWrapper(Tag())
        queryWrapper
            .eq(Tag::show, ShowEnum.SHOW.status)
        val list = JpaRepository.selectList(queryWrapper)
        return list.map {
            BlogTagCount(
                tagId = it?.tagId, tagName = it?.tagName, tagCount = blogTagService.count(
                    KtQueryWrapper(BlogTag())
                        .eq(BlogTag::tagId, it?.tagId)
                )
            )
        }

    }

    @Transactional(rollbackFor = [Exception::class])
    override fun clearTag(tagId: String?): Int {
        val queryWrapper = KtQueryWrapper(BlogTag::class.java)
            .eq(BlogTag::tagId, tagId)
        val blogTagList = blogTagService.list(queryWrapper)
        //如果存在不tag和blog的对应关系
        return if (blogTagList.isEmpty()) {
            tagMapper.delete(KtQueryWrapper(Tag::class.java).eq(Tag::tagId, tagId))
        } else {
            blogTagList.forEach {
                val tagList = blogTagService.list(KtQueryWrapper(BlogTag::class.java).eq(BlogTag::blogId, it?.blogId))
                    .map { it?.tagId }
                if (tagList.contains(SysConfigConstants.DEFAULT_TAG.configField)) {
                    blogTagService.removeById(it)
                } else {
                    blogTagService.updateById(it.apply {
                        if (it != null) {
                            it.tagId = SysConfigConstants.DEFAULT_TAG.configField
                        }
                    })
                }
            }
            //blogTagService.updateBatchById(blogTagList);
            tagMapper.deleteById(tagId)
        }
    }
}
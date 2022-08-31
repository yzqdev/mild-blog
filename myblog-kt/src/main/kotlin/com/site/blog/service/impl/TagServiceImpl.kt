package com.site.blog.service.impl

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
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
    ServiceImpl<TagMapper, Tag>(), TagService {
    override fun getBogTagCountForIndex(): List<BlogTagCount> {
        val queryWrapper =
            KtQueryWrapper (Tag())
        queryWrapper
            .eq(Tag::show, ShowEnum.SHOW.status)
        val list = baseMapper.selectList(queryWrapper)
        return list.map {
            BlogTagCount(
                tagId = it?.tagId, tagName = it?.tagName, tagCount = blogTagService.count(
                    KtQueryWrapper (BlogTag())
                         .eq(BlogTag::tagId, it?.tagId)
                )
            )
        }

    }

    @Transactional(rollbackFor = [Exception::class])
    override fun clearTag(tagId: String?): Int {
        val queryWrapper = Wrappers.lambdaQuery<BlogTag>()
            .eq(BlogTag::tagId, tagId)
        val blogTagList = blogTagService.list(queryWrapper)
        //如果存在不tag和blog的对应关系
        return if (blogTagList.isEmpty()) {
            tagMapper.delete(LambdaQueryWrapper<Tag>().eq(Tag::tagId, tagId))
        } else {
            blogTagList.forEach {
val tagList =blogTagService.list(QueryWrapper<BlogTag>().lambda().eq(BlogTag::blogId, it?.blogId )).map { it?.tagId }
                if (tagList.contains(SysConfigConstants.DEFAULT_TAG.configField)) {
                    blogTagService.removeById(it)
                } else {
                    blogTagService.updateById(it.apply {
                        if (it != null) {
                            it.tagId= SysConfigConstants.DEFAULT_TAG.configField
                        }
                    })
                }
            }
            //blogTagService.updateBatchById(blogTagList);
            tagMapper.deleteById(tagId)
        }
    }
}
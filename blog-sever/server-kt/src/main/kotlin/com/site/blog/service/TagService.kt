package com.site.blog.service

import com.baomidou.mybatisplus.extension.service.IService
import com.site.blog.model.entity.BlogTagCount
import com.site.blog.model.entity.Tag

/**
 *
 *
 * 标签表 服务类
 *
 *
 */
interface TagService : IService<Tag?> {
    /**
     * 让博客标签数指数
     *
     * @return [List]<[BlogTagCount]>
     */
    fun getBogTagCountForIndex(): List<BlogTagCount>

    /**
     * 清除标签
     *
     * @param tagId 标签id
     * @return boolean
     */
    fun clearTag(tagId: String?): Int
}
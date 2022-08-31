package com.site.blog.service

import com.baomidou.mybatisplus.extension.service.IService
import com.site.blog.model.entity.BlogInfo
import com.site.blog.model.entity.BlogTag

/**
 * @author yzq
 * @description
 * @date:Created time 2021/8/26 0:00
 * @modified By:
 */
interface BlogTagService : IService<BlogTag?> {
    fun removeAndsaveBatch(blogTagIds: List<String>, blogInfo: BlogInfo)
}
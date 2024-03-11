package com.site.blog.service

import com.baomidou.mybatisplus.extension.service.IService
import com.site.blog.model.entity.BlogConfig

/**
 *
 *
 * 服务类
 *
 *
 */
interface BlogConfigService : IService<BlogConfig?> {
    fun allConfigs(): Map<String, String>
}
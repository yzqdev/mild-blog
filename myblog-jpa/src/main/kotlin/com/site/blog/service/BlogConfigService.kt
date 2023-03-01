package com.site.blog.service


import com.site.blog.model.entity.BlogConfig

/**
 *
 *
 * 服务类
 *
 *
 */
interface BlogConfigService  {
    fun allConfigs(): Map<String, String>

    fun updateById(conf: BlogConfig)
}
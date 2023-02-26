package com.site.blog.constants.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * @author yanni
 * @date time 2022/6/16 2:54
 * @modified By:
 */
@Component
@ConfigurationProperties(value = "myblog")
data class MyBlog(
    var version: String? = null,
    var name: String? = null
)
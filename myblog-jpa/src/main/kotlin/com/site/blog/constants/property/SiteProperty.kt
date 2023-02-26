package com.site.blog.constants.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * @author yanni
 * @date time 2022/1/20 2:28
 * @modified By:
 */
@Component
@ConfigurationProperties(value = "myblog.site")
data class SiteProperty (
    var ip: String? = null,
    var  name: String? = null
    )
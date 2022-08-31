package com.site.blog.constants.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * 图片上传路径常量
 *
 * @date: 2019/8/24 15:16
 */
@Component
@ConfigurationProperties(prefix = "myblog.upload")
data class UploadProperty(
    var fileUrl: String? = null,
    var filePrefix: String? = null,
    var avatar: String? = null,
)
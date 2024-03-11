package com.site.blog.exception

/**
 * @author yanni
 * @date time 2022/6/17 15:58
 * @modified By:
 */
data class ServiceException(
    var code: Int,
    override var message: String?
) : RuntimeException()
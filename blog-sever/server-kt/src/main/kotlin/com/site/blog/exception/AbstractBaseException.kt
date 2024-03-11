package com.site.blog.exception

interface AbstractBaseException {
    val code: Int?
    val message: String?
}
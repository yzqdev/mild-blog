package com.site.blog.model.dto

import java.io.Serial
import java.io.Serializable

data class Result<T>  (
    var resultCode: Int? = null,
    var message: String? = null,
    var data: T? = null,

    var success: Boolean? = null,
    var timestamp: Long = 0



)
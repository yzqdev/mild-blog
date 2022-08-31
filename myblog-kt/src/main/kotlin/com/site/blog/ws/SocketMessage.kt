package com.site.blog.ws

import java.time.LocalDate

data class SocketMessage(
    var message: String? = null,
    var sendDate: LocalDate? = null
)
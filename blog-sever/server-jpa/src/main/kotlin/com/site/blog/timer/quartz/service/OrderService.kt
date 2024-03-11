package com.site.blog.timer.quartz.service

import com.site.blog.util.ColorUtil.Companion.green
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class OrderService {
    @Value("\${server.port}")
    private val port: String? = null
    fun showSwagger() {
        green("-----------------------------------------------")
        green("          http://localhost:$port/swagger-ui.html")
        green("          http://localhost:$port                ")
        green("          http://localhost:2800                ")
        green("-----------------------------------------------")
    }
}
package com.site.blog.config.listener

import org.springframework.boot.web.context.WebServerInitializedEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Configuration
import java.net.InetAddress
import java.net.UnknownHostException

/**
 * @author yanni
 * @date time 2021/10/29 12:13
 * @modified By:
 */
@Configuration
class AppListener : ApplicationListener<WebServerInitializedEvent> {
    override fun onApplicationEvent(event: WebServerInitializedEvent) {
        val port = event.applicationContext.webServer.port
        var address: InetAddress? = null
        try {
            address = InetAddress.getLocalHost()
            println("http://" + address.hostAddress + ":" + port + "/swagger-ui.html")
            println("http://" + address.hostAddress + ":" + port)
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        }
    }
}
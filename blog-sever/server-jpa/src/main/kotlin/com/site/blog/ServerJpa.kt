package com.site.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

//@RepositoryScan("com.site.blog.mapper")
@SpringBootApplication
class ServerJpa

fun main(args: Array<String>) {
    runApplication<ServerJpa>(*args)
}

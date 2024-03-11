package com.site.blog

import com.dtflys.forest.springboot.annotation.ForestScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

//@MapperScan("com.site.blog.mapper")
@SpringBootApplication
@ForestScan(basePackages = ["com.site.blog.client"])
class ServerKt

fun main(args: Array<String>) {
    runApplication<ServerKt>(*args)
}

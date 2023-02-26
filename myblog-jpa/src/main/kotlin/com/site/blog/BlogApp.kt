package com.site.blog

import com.dtflys.forest.springboot.annotation.ForestScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

//@RepositoryScan("com.site.blog.mapper")
@SpringBootApplication
class BlogApp

fun main(args: Array<String>) {
    runApplication<BlogApp>(*args)
}

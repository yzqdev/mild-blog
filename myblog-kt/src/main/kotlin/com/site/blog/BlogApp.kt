package com.site.blog

import com.dtflys.forest.springboot.annotation.ForestScan
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

//@MapperScan("com.site.blog.mapper")
@SpringBootApplication
@ForestScan(basePackages = ["com.site.blog.client"])
class BlogApp

fun main(args: Array<String>) {
    runApplication<BlogApp>(*args)
}

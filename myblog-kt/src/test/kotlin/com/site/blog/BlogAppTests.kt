package com.site.blog

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.site.blog.mapper.BlogConfigMapper
import com.site.blog.model.entity.BlogConfig
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.annotation.Resource


@SpringBootTest

class BlogAppTests() {

    @Resource
    lateinit var blogConfigMapper: BlogConfigMapper

    @Test
    fun getBlogConf() {
        val flag: Boolean = blogConfigMapper.exists(KtQueryWrapper (BlogConfig()).eq(BlogConfig::code, "init"))
        println("是否是第一次? ${!flag}")
    }

}

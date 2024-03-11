package com.site.blog.client

import com.dtflys.forest.annotation.Get
import com.site.blog.model.entity.Tag
import org.springframework.stereotype.Component


interface MyClient {
    @Get("http://localhost:2801/v2/home/tags")
    fun helloForest(): Result<List<Tag>>
}
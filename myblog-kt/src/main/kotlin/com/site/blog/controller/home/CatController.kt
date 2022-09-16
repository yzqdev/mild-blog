package com.site.blog.controller.home

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Yangzhengqian
 * @description
 * @date:Created time 2022/9/16 13:38
 * @modified By:
 *
 */
@RestController
@RequestMapping("/v2/cat")
@Tag(name = "cat", description = "模拟数据")
class CatController {
    @GetMapping("")
    fun getCats():String{
        return "cates"
    }
}
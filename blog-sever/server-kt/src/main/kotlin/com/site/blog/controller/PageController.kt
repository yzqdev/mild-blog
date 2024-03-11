package com.site.blog.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

/**
 * @author Yangzhengqian
 * @description
 * @date:Created time 2021/8/12 17:30
 * @modified By:
 */
@Controller
class PageController {
    @GetMapping("/index")
    fun index(): String {
        return "index"
    }
    @GetMapping("")

    fun home() :String{
        return "index"
    }
}
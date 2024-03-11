package com.site.blog.controller

import com.site.blog.client.MyClient
import com.site.blog.constants.HttpStatusEnum
import com.site.blog.util.BaseResult.getResultByHttp
import com.site.blog.util.ResultDto
import jakarta.annotation.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author yanni
 * @date time 2022/6/18 4:41
 * @modified By:
 */
//@RestController
//@RequestMapping("/v2/client")
class ClientController(var myClient: MyClient) {


//    @get:GetMapping("/home")
    val mihoyoHome: ResultDto<*>
        get() {
            val result = myClient.helloForest()
            return getResultByHttp(HttpStatusEnum.OK, true, result)
        }
}
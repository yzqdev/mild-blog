package com.site.blog.controller

import com.site.blog.client.MyClient
import com.site.blog.constants.HttpStatusEnum
import com.site.blog.model.dto.Result
import com.site.blog.util.ResultGenerator.getResultByHttp
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource

/**
 * @author yanni
 * @date time 2022/6/18 4:41
 * @modified By:
 */
@RestController
@RequestMapping("/v2/client")
class ClientController(var myClient: MyClient) {


    @get:GetMapping("/home")
    val mihoyoHome: Result<*>
        get() {
            val result = myClient.helloForest()
            return getResultByHttp(HttpStatusEnum.OK, true, result)
        }
}
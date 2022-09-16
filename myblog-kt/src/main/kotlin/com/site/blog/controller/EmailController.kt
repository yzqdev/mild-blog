package com.site.blog.controller

import com.site.blog.constants.HttpStatusEnum
import com.site.blog.service.MailService
import com.site.blog.util.Result.getResultByHttp
import com.site.blog.util.ResultDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author yanni
 * @date time 2022/1/24 22:53
 * @modified By:
 */
@RestController
@RequestMapping("/email")
class EmailController(private val mailService: MailService) {
    @Value("\${server.port}")
    private val port: String? = null
    @PostMapping("/sendTemplate")
    fun sendTemplate(to: String?, subject: String?): ResultDto<*> {
        val content = HashMap<String?, Any?>(16)
        val username = "yanni"
        val webname = "yzq"
        val new_pass = "123456"
        val domain = "http://localhost:$port"
        val uid = "yzq"
        content["username"] = username
        content["webname"] = webname
        content["new_pass"] = new_pass
        content["url"] = "$domain/user/retrieve?username=$uid"
        mailService.sendTemplateEmail(to, subject, content)
        return getResultByHttp(HttpStatusEnum.OK, true, content)
    }
}
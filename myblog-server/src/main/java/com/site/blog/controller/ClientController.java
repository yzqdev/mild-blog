package com.site.blog.controller;

import com.site.blog.client.MyClient;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.model.dto.Result;
import com.site.blog.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yanni
 * @date time 2022/6/18 4:41
 * @modified By:
 */
@RestController
@RequestMapping("/v2/client")
public class ClientController {
    @Resource
    private MyClient myClient;

    @GetMapping("/home")
    public Result getMihoyoHome() {
       var result = myClient.helloForest();
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, result);
    }


}

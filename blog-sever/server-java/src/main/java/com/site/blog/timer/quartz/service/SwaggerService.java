package com.site.blog.timer.quartz.service;

import com.site.blog.util.ColorUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SwaggerService {

    @Value("${server.port}")
    private String port;
    public void showSwagger() {
        ColorUtil.green("-----------------------------------------------");
        ColorUtil.green("          http://localhost:"+port+"/swagger-ui.html");
        ColorUtil.green("          http://localhost:"+port+"                ");
        ColorUtil.green("          http://localhost:2800                ");
        ColorUtil.green("-----------------------------------------------");
    }
}
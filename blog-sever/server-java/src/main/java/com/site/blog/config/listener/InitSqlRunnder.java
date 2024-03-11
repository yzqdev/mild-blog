package com.site.blog.config.listener;

import com.site.blog.service.InitService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

@Component // 注意 这里必须有
@Order(1) //如果有多个类需要启动后执行 order注解中的值为启动的顺序
public class InitSqlRunnder implements CommandLineRunner {

 @Resource
    InitService initService;

    @Override
    public void run(String... args) {
         initService.initUseEntity();
    }
}
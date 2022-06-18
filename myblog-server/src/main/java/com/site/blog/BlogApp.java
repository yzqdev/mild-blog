package com.site.blog;

import com.dtflys.forest.springboot.annotation.ForestScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 我的博客应用程序
 *
 * @author yanni
 * @date 2021/11/21
 */
@MapperScan("com.site.blog.mapper")
@SpringBootApplication
@ForestScan(basePackages = "com.site.blog.client")
public class BlogApp {
    public static void main(String[] args) {
        SpringApplication.run(BlogApp.class, args);
    }
}

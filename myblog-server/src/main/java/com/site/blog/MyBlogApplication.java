package com.site.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;


/**
 * 我的博客应用程序
 *
 * @author yanni
 * @date 2021/11/21
 */
@MapperScan("com.site.blog.mapper")
@SpringBootApplication

public class MyBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyBlogApplication.class, args);
    }
}

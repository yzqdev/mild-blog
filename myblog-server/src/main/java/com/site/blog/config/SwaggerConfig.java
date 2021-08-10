package com.site.blog.config;

import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableOpenApi
//@ComponentScan("com.qgzx.mapper")
public class SwaggerConfig {
    @Bean
    public Docket docker() {
        // 构造函数传入初始化规范，这是swagger2规范
        return new Docket(DocumentationType.OAS_30)
                //apiInfo： 添加api详情信息，参数为ApiInfo类型的参数，这个参数包含了第二部分的所有信息比如标题、描述、版本之类的，开发中一般都会自定义这些信息
                .apiInfo(apiInfo())
                .groupName("group")
                //配置是否启用Swagger，如果是false，在浏览器将无法访问，默认是true
                .enable(true)
                .select()
                //apis： 添加过滤条件,
                .apis(RequestHandlerSelectors.basePackage("com.site.blog.controller"))
                .paths(PathSelectors.any())
                //paths： 这里是控制哪些路径的api会被显示出来，比如下方的参数就是除了/user以外的其它路径都会生成api文档

                .build()
                /* 设置安全模式，swagger可以设置访问token */
                .securitySchemes(security())
                .securityContexts(securityContexts());

    }


    private List<SecurityScheme> security() {
        //这里第一个写header里面的字段名称，第二个无所谓(特别注意）
        ApiKey apiKey = new ApiKey("token", "token", In.HEADER.toValue());
        return Collections.singletonList(apiKey);
    }
    /**
     * 授权信息全局应用
     */
    private List<SecurityContext> securityContexts() {
        //还有这里
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(Collections.singletonList(new SecurityReference("token", new AuthorizationScope[]{new AuthorizationScope("global", "")})))
                        .build()
        );
    }


    private ApiInfo apiInfo() {
        Contact contact = new Contact("名字：name", "个人链接：http://xxx.xxx.com/", "邮箱：XXX");
        return new ApiInfo(
                "我的博客",
                "this is my own blog",
                "版本内容：v1.0.0",
                "链接：http://terms.service.url/",
                contact,
                "许可：Apach 2.0 ",
                "许可链接：XXX",
                new ArrayList<>()
        );
    }
}
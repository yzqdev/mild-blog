package com.site.blog.constants.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yanni
 * @date time 2022/6/16 2:54
 * @modified By:
 */
@Component
@Data
@ConfigurationProperties(value = "myblog")
public class MyBlog{
    private String version;
    private String name;
}

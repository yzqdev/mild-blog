package com.site.blog.constants.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yanni
 * @date time 2022/1/20 2:28
 * @modified By:
 */
@Component
@Data
@ConfigurationProperties(value = "myblog.site")
public class SiteProperty {
    private String ip;
    private String name;
}

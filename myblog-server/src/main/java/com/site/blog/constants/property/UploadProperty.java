package com.site.blog.constants.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 图片上传路径常量
 *
 * @date: 2019/8/24 15:16
 */
@Component
@Data
@ConfigurationProperties(prefix = "myblog.upload")
public class UploadProperty {
    private String fileUrl;
    private String filePrefix;


    private String avatar;
}

package com.site.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yanni
 * @date time 2022/6/18 1:23
 * @modified By:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailConfig {
    @TableId(type = IdType.ASSIGN_ID)
    private String id ;
    private String email;
    /**
     * 电子邮件的关键
     */
    private String emailKey;
    /**
     * 电子邮件网址
     */
    private String emailUrl;
    /**
     * 端口
     */
    private String port;
    private String emailName;
    private Boolean enable ;

}

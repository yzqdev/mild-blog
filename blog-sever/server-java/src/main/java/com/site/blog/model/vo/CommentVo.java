package com.site.blog.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.site.blog.model.entity.BlogInfo;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author yanni
 * @date time 2022/6/15 11:45
 * @modified By:
 */
@Data

public class CommentVo {
    private String id;
private String blogId;
  private BlogInfo blogInfo;

    /**
     * 评论者名称
     */
    private String commentator;

    /**
     * 评论人的邮箱
     */
    private String email;

    /**
     * 网址
     */
    private String websiteUrl;

    /**
     * 评论内容
     */
    private String commentBody;

    /**
     * 评论创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")    //将Date转换成String,一般后台传值给前台时
    private LocalDateTime commentCreateTime;

    /**
     * 评论时的ip地址
     */
    private String commentatorIp;
    private String userAgent;
    /**
     * 回复内容
     */
    private String replyBody;

    /**
     * 回复时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime replyCreateTime;

    private Boolean commentStatus;


    private String os;
}

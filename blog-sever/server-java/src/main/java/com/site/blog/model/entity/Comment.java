package com.site.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 评论信息表
 * </p>
 *
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Comment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
     
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 关联的blog主键
     */
    @TableField("blog_id")
    @NotNull(message = "非法请求")
    @Min(value = 0, message = "非法请求")
    private String blogId;

    /**
     * 评论者名称
     */
    @TableField("commentator")
    @NotBlank(message = "请输入称呼")
    @Length(min = 1, max = 6, message = "名称过长或过短")
    private String commentator;

    /**
     * 评论人的邮箱
     */
    @TableField("email")
    @Email(message = "邮箱地址不合法")
    private String email;

    /**
     * 网址
     */
    @TableField("website_url")
    private String websiteUrl;

    /**
     * 评论内容
     */
    @TableField("comment_body")
    @NotBlank(message = "请输入评论内容")
    @Length(min = 1, max = 200, message = "评论内容过长或过短")
    private String commentBody;

    /**
     * 评论创建时间
     */
    @TableField("comment_create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")    //将Date转换成String,一般后台传值给前台时
    private LocalDateTime commentCreateTime;

    /**
     * 评论时的ip地址
     */
    @TableField("commentator_ip")
    private String commentatorIp;
    @TableField("user_agent")
    private String userAgent;
    /**
     * 回复内容
     */
    @TableField("reply_body")
    private String replyBody;

    /**
     * 回复时间
     */
    @TableField("reply_create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime replyCreateTime;

    /**
     * 是否审核通过 0-未审核 1-审核通过
     */
    @TableField("comment_status")
    private Boolean commentStatus;

    /**
     * 是否删除 0-未删除 1-已删除
     */
    @TableField("deleted")
    private Boolean deleted;
    @TableField("os")
    private String os;


}

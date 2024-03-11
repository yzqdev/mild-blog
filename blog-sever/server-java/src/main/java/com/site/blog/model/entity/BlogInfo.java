package com.site.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 博客信息表
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class BlogInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 博客表主键id
     */
    @TableId(value = "blog_id", type = IdType.ASSIGN_ID)
    private String blogId;

    /**
     * 博客标题
     */
    @TableField("blog_title")
    private String blogTitle;

    /**
     * 博客自定义路径url
     */
@TableField("sub_url")
    private String subUrl;

    /**
     * 博客前言
     */
   @TableField("preface")
    private String preface;

    /**
     * 博客内容
     */
    @TableField("blog_content")
    private String blogContent;



    /**
     * 阅读量
     */
    @TableField("blog_views")
    private Long blogViews;

    /**
     * 0-允许评论 1-不允许评论
     */
    @TableField("enable_comment")
    private Boolean enableComment;

    /**
     * 是否删除 0=草稿 1=发布
     */
    @TableField("show")
    private Boolean show;

    /**
     * 添加时间,将Date转换成String,一般后台传值给前台时
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间//将Date转换成String,一般后台传值给前台时
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("update_time")
    private LocalDateTime updateTime;
@TableField("deleted")
    private Boolean deleted;

}

package com.site.blog.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yzq
 * @description
 * @date:Created time 2021/8/12 0:32
 * @modified By:
 */
@Data
public class BlogInfoDo {

    /**
     * 博客表主键id
     */
    private String blogId;

    /**
     * 博客标题
     */
    private String blogTitle;

    /**
     * 博客自定义路径url
     */

    private String blogSubUrl;
    /**
     * 博客前言
     */
    private String blogPreface;

    /**
     * 博客内容
     */
    private String blogContent;

    /**
     * 博客分类id
     */
    private String blogCategoryId;


    /**
     * 博客标签ids
     */

    private List<String> blogTagIds;
    /**
     * 0-草稿 1-发布
     */
    private Boolean show;

    /**
     * 阅读量
     */
    private Long blogViews;

    /**
     * 0-允许评论 1-不允许评论
     */
    //@TableField("enable_comment")
    private Boolean enableComment;

    /**
     * 是否删除 0=否 1=是
     */
    //@TableField("is_deleted")
    //private Integer isDeleted;

    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;


}

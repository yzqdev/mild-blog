package com.site.blog.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

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
    private Long blogId;

    /**
     * 博客标题
     */
    private String blogTitle;

    /**
     * 博客自定义路径url
     */


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
    private Integer blogCategoryId;

    /**
     * 博客分类(冗余字段)
     */


    /**
     * 博客标签(冗余字段)
     */

private String blogTags;
    /**
     * 0-草稿 1-发布
     */
    //@TableField("blog_status")
    //private Integer blogStatus;

    /**
     * 阅读量
     */
    private Long blogViews;

    /**
     * 0-允许评论 1-不允许评论
     */
    //@TableField("enable_comment")
    //private Integer enableComment;

    /**
     * 是否删除 0=否 1=是
     */
    //@TableField("is_deleted")
    //private Integer isDeleted;

    /**
     * 添加时间
     */


}

package com.site.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 博客分类
 * </p>
 *
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Category extends Model<Category> implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 分类表主键
     */
    @TableId(value = "category_id", type = IdType.ASSIGN_ID)
    private String categoryId;

    /**
     * 分类的名称
     */
    @TableField("category_name")
    private String categoryName;

    /**
     * 分类的图标
     */
    @TableField("category_icon")
    private String categoryIcon;

    /**
     * 分类的排序值 被使用的越多数值越大
     */
    @TableField("category_rank")
    private Integer categoryRank;

    /**
     * 是否删除 0=否 1=是
     */
    @TableField("show")
    private Boolean show;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
private LocalDateTime updateTime;

}

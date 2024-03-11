package com.site.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 友情链接表
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Link implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 友链表主键id
     */
    @TableId(value = "link_id", type = IdType.ASSIGN_ID)
    private String linkId;

    /**
     * 友链类别 0-友链 1-推荐 2-个人网站
     */
    @TableField("link_type")
    private Integer linkType;

    /**
     * 网站名称
     */
    @TableField("link_name")
    private String linkName;

    /**
     * 网站链接
     */
    @TableField("link_url")
    private String linkUrl;

    /**
     * 网站描述
     */
    @TableField("link_description")
    private String linkDescription;

    /**
     * 用于列表排序
     */
    @TableField("link_rank")
    private Integer linkRank;

    /**
     * 是否删除 0-未删除 1-已删除
     */
    @TableField("show")
    private Boolean show;

    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("create_time")
    private Timestamp createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   @TableField("update_time")
    private Timestamp updateTime;


}

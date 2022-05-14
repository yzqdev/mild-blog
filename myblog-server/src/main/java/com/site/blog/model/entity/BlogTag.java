package com.site.blog.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;

import java.io.Serial;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 博客跟标签的关系表
 * </p>
 *
 * @author: 南街
 * @since 2019-08-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogTag implements Serializable {

@Serial
private static final long serialVersionUID=1L;

    /**
     * 关系表id
     */
    @TableId(value = "relation_id", type = IdType.ASSIGN_ID)
    private String relationId;

    /**
     * 博客id
     */
    @TableField("blog_id")
    private String blogId;

    /**
     * 标签id
     */
    @TableField(value = "tag_id" )
    private String tagId;

    /**
     * 添加时间
     */
    @TableField("create_time")
    private Date createTime;


}

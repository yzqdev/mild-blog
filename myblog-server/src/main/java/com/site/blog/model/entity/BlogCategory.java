package com.site.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * @author yzq
 * @description
 * @date:Created time 2021/8/25 23:57
 * @modified By:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlogCategory  extends Model<BlogCategory> {
    @TableId(type = IdType.AUTO)
    private Integer relationId;
    private Long blogId;
    private Integer categoryId;
    private Timestamp createTime;
}

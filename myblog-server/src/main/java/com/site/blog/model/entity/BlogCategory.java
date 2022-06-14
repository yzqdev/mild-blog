package com.site.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author yzq
 * @description
 * @date:Created time 2021/8/25 23:57
 * @modified By:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogCategory  extends Model<BlogCategory> {
    /**
     * 关系id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String relationId;
    /**
     * 博客id
     */
    private Long blogId;
    /**
     * 类别id
     */
    private Long categoryId;
    /**
     * 创建时间
     */
    private Timestamp createTime;
}

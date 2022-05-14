package com.site.blog.model.vo;

import com.site.blog.model.entity.Tag;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class BlogEditVO {
    private String blogId;

    private String blogTitle;

    private String blogCategoryId;

    private Long commentCount;

    private String blogCategoryIcon;

    private Integer blogStatus;
    private String blogPreface;
    private String blogCoverImage;

    private Long blogViews;

    private List<String> blogTagIds;

    private String blogContent;

    private Integer enableComment;

    private Timestamp createTime;

}

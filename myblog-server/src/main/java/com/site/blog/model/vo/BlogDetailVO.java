package com.site.blog.model.vo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
@Data
public class BlogDetailVO {
    private Long blogId;

    private String blogTitle;

    private Integer blogCategoryId;

    private long commentCount;

    private String blogCategoryIcon;

    private String blogCategoryName;

    private String blogCoverImage;

    private Long blogViews;

    private List<String> blogTags;

    private String blogContent;

    private Integer enableComment;

    private Timestamp createTime;

}

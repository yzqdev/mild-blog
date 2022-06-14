package com.site.blog.model.vo;


import lombok.Data;

import java.time.LocalDateTime;

import java.util.List;

@Data
public class BlogEditVO {
    private Long blogId;

    private String blogTitle;

    private Long blogCategoryId;

    private Long commentCount;

    private String blogCategoryIcon;

    private Integer blogStatus;
    private String blogPreface;
    private String blogCoverImage;

    private Long blogViews;

    private List<Long> blogTagIds;

    private String blogContent;

    private Integer enableComment;

    private LocalDateTime createTime;

}

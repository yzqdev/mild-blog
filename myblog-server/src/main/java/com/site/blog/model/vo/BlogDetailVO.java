package com.site.blog.model.vo;

import com.site.blog.model.entity.Category;
import com.site.blog.model.entity.Tag;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BlogDetailVO {
    private String blogId;

    private String blogTitle;

    private Category blogCategory;

    private Long commentCount;

    private String blogCategoryIcon;
    private String blogPreface;

    private String blogCoverImage;

    private Long blogViews;

    private List<Tag> blogTags;
    private Integer blogStatus;
    private String blogContent;

    private Integer enableComment;
    private Integer isDeleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

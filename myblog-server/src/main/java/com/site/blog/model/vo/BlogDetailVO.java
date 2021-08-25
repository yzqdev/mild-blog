package com.site.blog.model.vo;

import io.swagger.models.auth.In;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
@Data
public class BlogDetailVO {
    private Long blogId;

    private String blogTitle;

    private Integer blogCategoryId;

    private Long commentCount;

    private String blogCategoryIcon;



    private String blogCoverImage;

    private Long blogViews;

    private List<Integer> blogTagIds;

    private String blogContent;

    private Integer enableComment;

    private Timestamp createTime;

}

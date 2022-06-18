package com.site.blog.model.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

import java.util.List;

@Data
public class BlogEditVO {
    private String blogId;

    private String blogTitle;

    private String blogCategoryId;

    private Long commentCount;

    private String blogCategoryIcon;

    private Boolean show;
    private String subUrl;
    private String preface;
    private String blogCoverImage;

    private Long blogViews;

    private List<String> blogTagIds;

    private String blogContent;

    private Boolean enableComment;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

}

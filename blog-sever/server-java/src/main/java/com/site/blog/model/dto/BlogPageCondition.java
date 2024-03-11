package com.site.blog.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class BlogPageCondition {

    private Integer pageNum;

    private Integer pageSize;

    private String keyword;

    private String pageName;

    private String tagId;

    private String categoryId;

}

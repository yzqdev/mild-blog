package com.site.blog.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlogTagCount {
    private Long tagId;

    private String tagName;

    private long tagCount;



}

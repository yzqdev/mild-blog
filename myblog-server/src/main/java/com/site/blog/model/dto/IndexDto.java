package com.site.blog.model.dto;

import com.site.blog.model.entity.BlogConfig;
import lombok.Data;

import java.util.List;


@Data
public class IndexDto {
    private int categoryCount;
    private int blogCount;
    private int linkCount;
    private int tagCount;
    private int commentCount;
    private List<BlogConfig> sysList;
}

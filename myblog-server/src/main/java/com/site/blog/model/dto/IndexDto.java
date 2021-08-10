package com.site.blog.model.dto;

import com.site.blog.model.entity.BlogConfig;
import lombok.Data;

import java.util.List;

/**
 * @Author: Yangzhengqian
 * @Description:
 * @Date:Created time 2020/11/10 下午1:54
 * @Modified By:
 */
@Data
public class IndexDto {
    private int categoryCount;
    private int blogCount;
    private int linkCount;
    private int tagCount;
    private int commentCount;
    private List<BlogConfig> sysList;
}

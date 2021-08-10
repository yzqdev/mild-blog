package com.site.blog.model.dto;

import com.site.blog.model.entity.BlogLink;
import lombok.Data;

import java.util.List;

/**
 * @Author: Yangzhengqian
 * @Description:
 * @Date:Created time 2020/11/9 下午1:33
 * @Modified By:
 */
@Data
public class BlogLinkDto {
    List<BlogLink> favoriteLinks;
    List<BlogLink> recommendLinks ;
    List<BlogLink> personalLinks;

}

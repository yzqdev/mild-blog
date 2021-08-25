package com.site.blog.model.dto;

import com.site.blog.model.entity.Link;
import lombok.Data;

import java.util.List;

/**
 * @Author: Yangzhengqian
 * @Description:
 * @Date:Created time 2020/11/9 下午1:33
 * @Modified By:
 */
@Data
public class LinkDto {
    List<Link> favoriteLinks;
    List<Link> recommendLinks ;
    List<Link> personalLinks;

}

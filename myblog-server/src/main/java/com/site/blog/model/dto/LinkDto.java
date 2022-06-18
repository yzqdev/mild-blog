package com.site.blog.model.dto;

import com.site.blog.model.entity.Link;
import lombok.Data;

import java.util.List;


@Data
public class LinkDto {
    List<Link> favoriteLinks;
    List<Link> recommendLinks ;
    List<Link> personalLinks;

}

package com.site.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yzq
 * @description
 * @date:Created time 2021/8/25 19:56
 * @modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto {
    private Integer pageNum;
    private Integer pageSize;
}

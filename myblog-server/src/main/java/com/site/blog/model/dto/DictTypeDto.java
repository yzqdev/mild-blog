package com.site.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yanni
 * @date time 2022/6/17 13:33
 * @modified By:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictTypeDto {
    String name;
    String code;
    Integer sort;
    String remark;
}

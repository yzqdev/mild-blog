package com.site.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yanni
 * @date time 2022/6/17 14:35
 * @modified By:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictDataDto {
    String typeId;
    String value;
    String code;
    Integer sort;
    String remark;
}

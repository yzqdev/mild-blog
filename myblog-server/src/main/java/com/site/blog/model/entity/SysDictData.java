package com.site.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author yanni
 * @date time 2022/6/17 12:33
 * @modified By:
 */
@Data

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysDictData {
    @TableId(type = IdType.ASSIGN_ID)
    String id;
   String typeId;
   String value;
    String code;
    Integer sort;
    String remark;
    Boolean status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    LocalDateTime updateTime;
}

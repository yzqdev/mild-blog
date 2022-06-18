package com.site.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author yanni
 * @date time 2022/6/17 15:47
 * @modified By:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysTimer {
    @TableId(type = IdType.ASSIGN_ID)
    private  String id;
    private  String timerName;
    private  String actionClass;
    private  String cron;
    private  Boolean status;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}

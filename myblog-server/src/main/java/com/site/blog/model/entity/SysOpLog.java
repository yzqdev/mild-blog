package com.site.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yanni
 * @date time 2022/6/16 2:04
 * @modified By:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysOpLog implements Serializable {
    private static final long serialVersionUID=1L;
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String name;
    private String opType;
    private  String message;
    private  String ip;
    private  String location;
    private  String browser;
    private  String os;
    private  String url;
    private  String className;
    private  String methodName;
    private  String reqMethod;
    private  String param;
    private  String result;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime opTime;
    private  String account;
}

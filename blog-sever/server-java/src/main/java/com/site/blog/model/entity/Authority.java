package com.site.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author
 * @email
 * @date 2023-07-24 17:43:19
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@TableName("authority")
public class Authority implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String authority;
    private LocalDateTime createTime;
    private String createUser;

}

package com.site.blog.model.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.fasterxml.jackson.annotation.JsonFormat
import java.io.Serial
import java.io.Serializable
import java.time.LocalDateTime
 
data class BlogConfig  (  
    @TableId(type = IdType.ASSIGN_ID)
    var  id: String? = null,

    /**
     * 字段名
     */
    var  code: String = "",

    /**
     * 配置名
     */
    var  name: String = "",

    /**
     * 配置项的值
     */
    var  value: String? = null,

    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var  createTime: LocalDateTime? = null,

    /**
     * 修改时间
     */
    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var  updateTime: LocalDateTime? = null)

     
 
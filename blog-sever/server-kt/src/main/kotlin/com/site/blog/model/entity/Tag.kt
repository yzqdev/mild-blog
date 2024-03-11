package com.site.blog.model.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import java.io.Serializable
import java.time.LocalDateTime

/**
 *
 *
 * 标签表
 *
 *
 */
data class Tag(
    /**
     * 标签表主键id
     */
    @TableId(value = "tag_id", type = IdType.ASSIGN_ID)
    var tagId: String? = null,

    /**
     * 标签名称
     */
    @TableField("tag_name")
    var tagName: String? = null,

    /**
     * 是否删除 0=否 1=是
     */
    @TableField("show")
    var show: Boolean? = null,

    /**
     * 创建时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("create_time")
    var createTime: LocalDateTime? = null,

    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var updateTime: LocalDateTime? = null,
)
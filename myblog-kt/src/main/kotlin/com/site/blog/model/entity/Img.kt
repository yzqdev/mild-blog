package com.site.blog.model.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.extension.activerecord.Model
import com.fasterxml.jackson.annotation.JsonFormat
import java.io.Serial
import java.io.Serializable
import java.time.LocalDateTime

/**
 * @author yanni
 * @date time 2021/10/29 12:42
 * @modified By:
 */
data class Img(
    @TableId(type = IdType.ASSIGN_ID)
    var id: String? = null,
    var imgName: String? = null,

    /**
     * 图片系统路径
     */
    var imgPath: String? = null,
    var imgSize: Long? = null,
    var imgUrl: String? = null,
    var thumbnailPath: String? = null,
    var imgType: String? = null,
    var mediaType: String? = null,
    var md5: String? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var uploadTime: LocalDateTime? = null,
) : Model<Img?>()
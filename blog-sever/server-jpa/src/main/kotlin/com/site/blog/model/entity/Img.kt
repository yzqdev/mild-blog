package com.site.blog.model.entity


import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.io.Serial
import java.io.Serializable
import java.time.LocalDateTime

/**
 * @author yanni
 * @date time 2021/10/29 12:42
 * @modified By:
 */
@Entity
data class Img(

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
)  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    open var id: Long? = null
}
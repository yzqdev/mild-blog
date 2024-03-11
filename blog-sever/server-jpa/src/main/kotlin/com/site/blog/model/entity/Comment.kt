package com.site.blog.model.entity


import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.io.Serial
import java.io.Serializable
import java.time.LocalDateTime
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

/**
 *
 *
 * 评论信息表
 *
 *
 */
@Entity
class Comment(
    /**
     * 主键id
     */


    /**
     * 关联的blog主键
     */

    var blogId: @NotNull(message = "非法请求") @Min(value = 0, message = "非法请求") String? = null,

    /**
     * 评论者名称
     */
    
//    @Length(min = 1, max = 6, message = "名称过长或过短")
    var commentator: @NotBlank(message = "请输入称呼") String? = null,

    /**
     * 评论人的邮箱
     */
    
    var email: @Email(message = "邮箱地址不合法") String? = null,

    /**
     * 网址
     */
    
    var websiteUrl: String? = null,

    /**
     * 评论内容
     */
    
//    @Length(min = 1, max = 200, message = "评论内容过长或过短")
    var commentBody: @NotBlank(message = "请输入评论内容") String? = null,

    /**
     * 评论创建时间
     */
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") //将Date转换成String,一般后台传值给前台时

    var commentCreateTime: LocalDateTime? = null,

    /**
     * 评论时的ip地址
     */
    
    var commentatorIp: String? = null,

    
    var userAgent: String? = null,

    /**
     * 回复内容
     */
    
    var replyBody: String? = null,

    /**
     * 回复时间
     */
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var replyCreateTime: LocalDateTime? = null,

    /**
     * 是否审核通过 0-未审核 1-审核通过
     */
    
    var commentStatus: Boolean? = null,

    /**
     * 是否删除 0-未删除 1-已删除
     */
    
    var deleted: Boolean? = null,

    
    var os: String? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    open var id: Long? = null
}
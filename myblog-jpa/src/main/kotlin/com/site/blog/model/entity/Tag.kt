package com.site.blog.model.entity

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import jakarta.persistence.*
import org.hibernate.Hibernate
import java.io.Serializable
import java.time.LocalDateTime

/**
 *
 *
 * 标签表
 *
 *
 */
@Entity
data class Tag(
    /**
     * 标签表主键id
     */


    /**
     * 标签名称
     */
    
    var tagName: String? = null,

    /**
     * 是否删除 0=否 1=是
     */
    
    var show: Boolean? = null,

    /**
     * 创建时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    
    var createTime: LocalDateTime? = null,

    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var updateTime: LocalDateTime? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "tag_id", nullable = false)
    open var tagId: Long? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Tag

        return tagId != null && tagId == other.tagId
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(tagId = $tagId , tagName = $tagName , show = $show , createTime = $createTime , updateTime = $updateTime )"
    }
}
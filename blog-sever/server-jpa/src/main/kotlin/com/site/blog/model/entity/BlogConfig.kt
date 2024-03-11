package com.site.blog.model.entity


import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.LocalDateTime
 @Entity
data class BlogConfig  (


    /**
     * 字段名
     */
    var  configCode: String  = "",

    /**
     * 配置名
     */
    var  configName: String  = "",

    /**
     * 配置项的值
     */
    var  configValue: String? = null,

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var  createTime: LocalDateTime? = null,

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var  updateTime: LocalDateTime? = null) {
     @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE)
     @Column(name = "id", nullable = false)
     open var id: Long? = null
 }

     
 
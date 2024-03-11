package com.site.blog.model.entity

import jakarta.persistence.*


/**
 * @author yanni
 * @date time 2022/6/18 1:23
 * @modified By:
 */
@Entity
data class EmailConfig(

    var email: String? = null,

    /**
     * 电子邮件的关键
     */
    var emailKey: String? = null,

    /**
     * 电子邮件网址
     */
    var emailUrl: String? = null,

    /**
     * 端口
     */
    var port: String? = null,
    var emailName: String? = null,
    var enable: Boolean? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    open var id: Long? = null
}
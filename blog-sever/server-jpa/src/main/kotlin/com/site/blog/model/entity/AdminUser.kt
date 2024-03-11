package com.site.blog.model.entity


import jakarta.persistence.*
import org.hibernate.Hibernate
import java.io.Serial
import java.io.Serializable

/**
 *
 *
 * 后台管理员信息表
 *
 *
 */
@Entity
data class AdminUser(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    open var id: Long? = null,

    /**
     * 管理员登陆名称
     */
    var  username: String  = "",

    /**
     * 管理员登陆密码
     */
    var  password: String  = "",

    /**
     * 管理员显示昵称
     */
    var  nickname: String? = null,

    /**
     * 是否锁定 0未锁定 1已锁定无法登陆
     */
    var  locked: Boolean? = null,
    var  uuid: String? = null,

    /**
     * 0  普通用户,1管理员
     */
    var  role: Int? = null,
    var  avatar: String? = null,
    var  email: String? = null,


    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as AdminUser

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , username = $username , password = $password , nickname = $nickname , locked = $locked , uuid = $uuid , role = $role , avatar = $avatar , email = $email )"
    }

}
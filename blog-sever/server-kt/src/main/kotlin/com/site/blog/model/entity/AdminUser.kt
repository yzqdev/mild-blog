package com.site.blog.model.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.extension.activerecord.Model
import java.io.Serial
import java.io.Serializable

/**
 *
 *
 * 后台管理员信息表
 *
 *
 */
data class AdminUser(
    @TableId(type = IdType.ASSIGN_UUID)
    var  id: String? = null,

    /**
     * 管理员登陆名称
     */
    @TableField("username")
    var  username: String? = null,

    /**
     * 管理员登陆密码
     */
    @TableField("password")
    var  password: String? = null,

    /**
     * 管理员显示昵称
     */
    @TableField("nickname")
    var  nickname: String? = null,

    /**
     * 是否锁定 0未锁定 1已锁定无法登陆
     */
    @TableField("locked")
    var  locked: Boolean? = null,
    var  uuid: String? = null,

    /**
     * 0  普通用户,1管理员
     */
    var  role: Int? = null,
    var  avatar: String? = null,
    var  email: String? = null,


    ) : Model<AdminUser?>(), Serializable
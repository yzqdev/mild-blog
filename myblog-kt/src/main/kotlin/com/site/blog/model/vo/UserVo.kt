package com.site.blog.model.vo

/**
 * @author yanni
 * @date time 2022/6/15 15:47
 * @modified By:
 */
data class UserVo  (
    var id: String? = null,

    /**
     * 管理员登陆名称
     */
    var username: String? = null,

    /**
     * 管理员显示昵称
     */
    var nickname: String? = null,

    /**
     * 是否锁定 0未锁定 1已锁定无法登陆
     */
    var locked: Boolean? = null,

    /**
     * 0  普通用户,1管理员
     */
    var role: Int? = null,
    var avatar: String? = null,
    var email: String? = null,
    var uuid: String? = null,
    )
package com.site.blog.service


import com.site.blog.model.entity.AdminUser

/**
 *
 *
 * 后台管理员信息表 服务类
 *
 *
 */
interface AdminUserService  {
    fun validatePassword(userId: String, oldPwd: String): Boolean
    fun updateUserInfo(adminUser: AdminUser): AdminUser
    fun register(user: AdminUser): AdminUser

    /**
     * 根据adminuserid获取用户
     * @param id
     * @return
     */
    fun getAdminUserById(id: Long): AdminUser
    fun removeById(id: Long): Any
      fun updateById(user: AdminUser)


}
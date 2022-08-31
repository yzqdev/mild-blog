package com.site.blog.service

import com.baomidou.mybatisplus.extension.service.IService
import com.site.blog.model.entity.AdminUser

/**
 *
 *
 * 后台管理员信息表 服务类
 *
 *
 */
interface AdminUserService : IService<AdminUser?> {
    fun validatePassword(userId: String?, oldPwd: String?): Boolean
    fun updateUserInfo(adminUser: AdminUser?): Boolean
    fun register(user: AdminUser?): Int

    /**
     * 根据adminuserid获取用户
     * @param id
     * @return
     */
    fun getAdminUserById(id: String?): AdminUser?
}
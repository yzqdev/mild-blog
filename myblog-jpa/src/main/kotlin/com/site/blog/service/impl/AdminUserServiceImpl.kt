package com.site.blog.service.impl


import com.site.blog.mapper.AdminUserMapper
import com.site.blog.model.entity.AdminUser
import com.site.blog.service.AdminUserService
import com.site.blog.util.MD5Utils.MD5Encode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.ObjectUtils

/**
 *
 *
 * 后台管理员信息表 服务实现类
 *
 *
 * @since 2019-08-25
 */
@Service
class AdminUserServiceImpl(private val adminUserMapper: AdminUserMapper) :  
    AdminUserService {
    /**
     * @Description: 验证密码
     * @Param: [userId, oldPwd]
     * @return: boolean
     * @date: 2019/8/26 13:27
     */
    override fun validatePassword(userId: String, oldPwd: String): Boolean {
        val queryWrapper: QueryWrapper<AdminUser> = QueryWrapper(
         AdminUser(userId ,password=MD5Encode(oldPwd, "UTF-8"))
        )

        val adminUser = adminUserMapper.selectOne(queryWrapper)
        return !ObjectUtils.isEmpty(adminUser)
    }

    /**
     * 更新用户信息
     *
     * @param adminUser 管理用户
     * @return boolean
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun updateUserInfo(adminUser: AdminUser): Boolean {
        adminUser!!.password = MD5Encode(adminUser.password, "UTF-8")
        return SqlHelper.retBool(adminUserMapper.updateById(adminUser))
    }

    override fun register(admin: AdminUser): Int {
        admin!!.password = MD5Encode(admin.password, "UTF-8")
        return adminUserMapper.insert(admin)
    }

    override fun getAdminUserById(id: String): AdminUser {
        return try {
            val queryWrapper = KtQueryWrapper (AdminUser())
            queryWrapper.eq(AdminUser::id, id)
            adminUserMapper.selectOne(queryWrapper)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
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


        val adminUser = adminUserMapper.findAdminUserByUsernameAndPassword(userId,MD5Encode(oldPwd, "UTF-8"))
        return !ObjectUtils.isEmpty(adminUser)
    }

    /**
     * 更新用户信息
     *
     * @param adminUser 管理用户
     * @return boolean
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun updateUserInfo(adminUser: AdminUser):AdminUser {
        adminUser!!.password = MD5Encode(adminUser.password, "UTF-8")
        return  adminUserMapper.save(adminUser)
    }

    override fun register(admin: AdminUser): AdminUser {
        admin!!.password = MD5Encode(admin.password, "UTF-8")
        return adminUserMapper.save(admin)
    }

    override fun getAdminUserById(id: Long): AdminUser {
        return     adminUserMapper.findAdminUserById(id)

    }



    override fun updateById(user: AdminUser) {
        adminUserMapper.save(user)
    }

    override fun removeById(id: Long): Any {
      return  adminUserMapper.deleteById(id)
    }
}
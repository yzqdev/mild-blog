package com.site.blog.mapper

import com.site.blog.model.entity.AdminUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 *
 *
 * 后台管理员信息表 Mapper 接口
 *
 *
 * @author zhulin
 * @since 2019-08-25
 */
@Repository
interface AdminUserMapper : JpaRepository<AdminUser,Long>
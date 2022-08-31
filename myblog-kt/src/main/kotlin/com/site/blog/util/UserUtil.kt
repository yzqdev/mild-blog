package com.site.blog.util

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.site.blog.model.entity.AdminUser


object UserUtil {
    fun getUserByUserCode(userCode: String?): AdminUser? {
        val u = AdminUser()
        u.id = userCode
        return u.selectOne(QueryWrapper(u))
    }
}
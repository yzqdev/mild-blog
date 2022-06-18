package com.site.blog.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.site.blog.model.entity.AdminUser;


public class UserUtil {
    public static AdminUser getUserByUserCode(String userCode) {

        AdminUser u = new AdminUser();
        u.setId(userCode);

        return u.selectOne(new QueryWrapper<>(u));

    }
}

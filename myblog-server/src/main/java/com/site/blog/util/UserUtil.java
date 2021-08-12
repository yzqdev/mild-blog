package com.site.blog.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.site.blog.model.entity.AdminUser;

/**
 * @author yzq
 * @description
 * @date:Created time 2021/8/11 22:02
 * @modified By:
 */
public class UserUtil {
    public static AdminUser getUserByUserCode(int userCode) {

        AdminUser u = new AdminUser();
        u.setAdminUserId(userCode);

        return u.selectOne(new QueryWrapper<>(u));

    }
}
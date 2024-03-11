package com.site.blog.service;

import com.site.blog.model.entity.AdminUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 后台管理员信息表 服务类
 * </p>
 *
 */
public interface AdminUserService extends IService<AdminUser> {

    boolean validatePassword(String userId, String oldPwd);

    boolean updateUserInfo(AdminUser adminUser);

    int register(AdminUser user);

    /**
     * 根据adminuserid获取用户
     * @param id
     * @return
     */
    AdminUser getAdminUserById(String id);
}

package com.site.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.site.blog.model.entity.AdminUser;
import com.site.blog.mapper.AdminUserMapper;
import com.site.blog.service.AdminUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.util.MD5Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 后台管理员信息表 服务实现类
 * </p>
 *
 * @author: 南街
 * @since 2019-08-25
 */
@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {


    private final AdminUserMapper adminUserMappe;

    /**
     * @Description: 验证密码
     * @Param: [userId, oldPwd]
     * @return: boolean
     * @date: 2019/8/26 13:27
     */
    @Override
    public boolean validatePassword(Integer userId, String oldPwd) {
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>(
                new AdminUser().setAdminUserId(userId)
                        .setLoginPassword(MD5Utils.MD5Encode(oldPwd, "UTF-8"))
        );

        AdminUser adminUser = adminUserMappe.selectOne(queryWrapper);
        return !ObjectUtils.isEmpty(adminUser);
    }

    @Transactional
    @Override
    public boolean updateUserInfo(AdminUser adminUser) {
        return SqlHelper.retBool(adminUserMappe.updateById(adminUser));
    }

    @Override
    public int register(AdminUser admin) {
        admin.setLoginPassword(MD5Utils.MD5Encode(admin.getLoginPassword(), "UTF-8"));
        int flag = adminUserMappe.insert(admin);
        return flag;
    }

    @Override
    public AdminUser getAdminUserById(Integer id) {

        try {
            QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("admin_user_id", id);
            return adminUserMappe.selectOne(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

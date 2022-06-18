package com.site.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.site.blog.service.AdminUserService;
import com.site.blog.model.entity.AdminUser;
import com.site.blog.mapper.AdminUserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.util.MD5Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 后台管理员信息表 服务实现类
 * </p>
 *
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
    public boolean validatePassword(String userId, String oldPwd) {
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>(
                new AdminUser().setId(userId)
                        .setPassword(MD5Utils.MD5Encode(oldPwd, "UTF-8"))
        );

        AdminUser adminUser = adminUserMappe.selectOne(queryWrapper);
        return !ObjectUtils.isEmpty(adminUser);
    }

    /**
     * 更新用户信息
     *
     * @param adminUser 管理用户
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateUserInfo(AdminUser adminUser) {
        adminUser.setPassword(MD5Utils.MD5Encode(adminUser.getPassword(),"UTF-8"));
        return SqlHelper.retBool(adminUserMappe.updateById(adminUser));
    }

    @Override
    public int register(AdminUser admin) {
        admin.setPassword(MD5Utils.MD5Encode(admin.getPassword(), "UTF-8"));
        int flag = adminUserMappe.insert(admin);
        return flag;
    }

    @Override
    public AdminUser getAdminUserById(String id) {

        try {
            LambdaQueryWrapper<AdminUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(AdminUser::getId, id);
            return adminUserMappe.selectOne(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

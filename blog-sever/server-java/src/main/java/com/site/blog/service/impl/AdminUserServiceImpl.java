package com.site.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.site.blog.service.AdminUserService;
import com.site.blog.model.entity.AdminUser;
import com.site.blog.mapper.AdminUserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 后台管理员信息表 服务实现类
 * </p>
 *
 * @since 2019-08-25
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {

    private final AdminUserMapper adminUserMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * 验证密码
     */
    @Override
    public boolean validatePassword(String userId, String oldPwd) {
        LambdaQueryWrapper<AdminUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminUser::getId, userId);
        AdminUser adminUser = adminUserMapper.selectOne(queryWrapper);
        if (adminUser == null) {
            return false;
        }
        return passwordEncoder.matches(oldPwd, adminUser.getPassword());
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
        adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword() ));
        return SqlHelper.retBool(adminUserMapper.updateById(adminUser));
    }

    @Override
    public int register(AdminUser admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword() ));
        return adminUserMapper.insert(admin);
    }

    @Override
    public AdminUser getAdminUserById(String id) {
        try {
            LambdaQueryWrapper<AdminUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(AdminUser::getId, id);
            return adminUserMapper.selectOne(queryWrapper);
        } catch (Exception e) {
            log.error("获取用户信息失败, id={}", id, e);
            return null;
        }
    }
}

package com.site.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.site.blog.mapper.AdminUserMapper;
import com.site.blog.mapper.AuthorityMapper;
import com.site.blog.model.entity.AdminUser;
import com.site.blog.model.entity.Authority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yzqde
 * @date time 2024/6/24 6:32
 * @modified By:
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AdminUserMapper adminUserMapper;
    private final AuthorityMapper authorityMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new BadCredentialsException("未上传用户名");
        }
        var user = adminUserMapper.selectOne(new QueryWrapper<AdminUser>().eq("username", username));
        if (user == null) {
            throw new BadCredentialsException("用户不存在");
        }
        // 封装自定义用户信息
        var userDetails =  UserDetailsImpl.createUser(user);
        // 补充权限信息
        List<Authority> list = authorityMapper.selectList(new QueryWrapper<Authority>().eq("username", user.getUsername()));

        if (!CollectionUtils.isEmpty(list)) {
            log.info(list.stream().map(Authority::getAuthority).collect(Collectors.joining(",")));
            for (Authority authority : list) {
                userDetails.getAuthorities().add(new SimpleGrantedAuthority(authority.getAuthority()));
            }
        }
        // 返回结果
        return userDetails;
    }
}

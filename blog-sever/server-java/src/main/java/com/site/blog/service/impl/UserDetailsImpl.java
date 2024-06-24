package com.site.blog.service.impl;

import com.site.blog.model.entity.AdminUser;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author yzqde
 * @date time 2024/6/24 6:34
 * @modified By:
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {
    List<GrantedAuthority> authorities=new ArrayList<>();
    private AdminUser adminUser;
    public static UserDetailsImpl createUser(AdminUser adminUser){
        UserDetailsImpl userDetails=new UserDetailsImpl();
        userDetails.adminUser=adminUser;
        return userDetails;
    }
    @Override
    public Collection< GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return adminUser.getPassword();
    }

    @Override
    public String getUsername() {
        return adminUser.getUsername();
    }

    @Override
    public boolean isEnabled() {
        return !adminUser.getLocked();
    }
}

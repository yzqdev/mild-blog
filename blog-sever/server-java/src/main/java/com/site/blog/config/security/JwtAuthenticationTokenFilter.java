package com.site.blog.config.security;

import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.site.blog.constants.BaseConstants;
import com.site.blog.model.entity.AdminUser;
import com.site.blog.model.vo.UserVo;
import com.site.blog.service.AdminUserService;
import com.site.blog.service.impl.UserDetailsServiceImpl;
import com.site.blog.util.JwtService;
import com.site.blog.util.UserUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author yzqde
 */
 @Component
@Slf4j
 @RequiredArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    private final UserDetailsServiceImpl userDetailsService;

private final   AdminUserService adminUserService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // get token from header:  Authorization: Bearer <token>
        String authHeader = request.getHeader(BaseConstants.TOKEN);
        if (Objects.isNull(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authToken = authHeader;
        log.info("authToken:{}", authToken);
        //verify token
        if (!JwtService.verifyToken(authToken )) {
            log.info("invalid token");
            filterChain.doFilter(request, response);
            return;
        }

        final String userName = (String) JwtService.getUsername(authToken) ;
        AdminUser user = adminUserService.getOne( new QueryWrapper<AdminUser>().eq("username",userName));
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        log.info("这里设置用户到session");
        log.info("user={}","myid");
        log.info("user=" + user);
        request.setAttribute(BaseConstants.USER_ATTR, userVo);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        // 注意，这里使用的是3个参数的构造方法，此构造方法将认证状态设置为true
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        var auths = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        log.info("uses auth is -> {}", auths);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        //将认证过了凭证保存到security的上下文中以便于在程序中使用


        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request, response);
    }
}

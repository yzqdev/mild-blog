package com.site.blog.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.HexUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.constants.SessionConstants;
import com.site.blog.constants.SysConfigConstants;
import com.site.blog.context.ConfigContextHolder;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.AdminUser;
import com.site.blog.model.vo.UserVo;
import com.site.blog.service.AdminUserService;
import com.site.blog.service.BlogConfigService;
import com.site.blog.service.MailService;
import com.site.blog.util.JwtService;
import com.site.blog.util.RequestHelper;
import com.site.blog.util.ResultGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

/**
 * @author yanni
 * @date time 2022/6/17 20:37
 * @modified By:
 */
@Controller
@RequestMapping("/v2/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AdminUserService adminUserService;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final BlogConfigService blogConfigService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/login")
    @ResponseBody
    public Result login(String username, String password,
            HttpSession session) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>(
                new AdminUser().setUsername(username));
        AdminUser adminUser = adminUserService.getOne(queryWrapper);
        if (adminUser != null) {
            if (!adminUser.getLocked()) {
                String token = JwtService.sign(adminUser.getUsername(), adminUser.getId());

                session.setAttribute(SessionConstants.LOGIN_USER, adminUser.getNickname());
                session.setAttribute(SessionConstants.LOGIN_USER_ID, adminUser.getId());
                session.setAttribute(SessionConstants.LOGIN_USER_NAME, adminUser.getUsername());
                session.setAttribute(SessionConstants.AUTHOR_IMG, blogConfigService.getById(
                        SysConfigConstants.SYS_AUTHOR_IMG.getConfigField()));

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, password);
                Authentication authenticate = authenticationManager.authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authenticate);
                return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, token);
            } else {
                return ResultGenerator.getResultByHttp(HttpStatusEnum.UNAUTHORIZED, false, "账户已被冻结");
            }
        } else {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.UNAUTHORIZED);
        }
    }

    /**
     * 获取html中的内容
     */

    @PostMapping(value = "/reg")
    @ResponseBody
    public Result<String> register(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        var queryWrapper = new LambdaQueryWrapper<AdminUser>().eq(AdminUser::getUsername, username);
        AdminUser adminUser = adminUserService.getOne(queryWrapper);
        if (adminUser != null) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST, false, "用户名已存在");
        } else {
            AdminUser regUser = AdminUser.builder().username(username).password(password)
                    .uuid(UUID.fastUUID().toString()).locked(true).role(0).build();

            adminUserService.register(regUser);

            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, "注册成功");

        }
    }

    @PostMapping("/findPassByMail/{email}")
    @ResponseBody
    public Result<String> findPassEmail(@PathVariable("email") String email) {
        mailService.sendFindPassEmail(email, mailService.getDefaultMail());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, "已发送邮件");
    }

    @PostMapping("/findPass")
    @ResponseBody
    public Result<UserVo> sendFindPassEmail() {
        var user = RequestHelper.getSessionUser();
        mailService.sendFindPassEmail(user.getEmail(), mailService.getDefaultMail());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, user);
    }

    /**
     * 密码重置确认页面 - 已弃用，保留用于向后兼容
     * 新流程应使用邮箱中的 token 进行验证
     */
    @Deprecated
    @GetMapping("/findPass")
    public String findPass(Model model, @RequestParam("email") String email, @RequestParam("cip") String cip) {
        try {
            var sysUser = adminUserService.getOne(new LambdaQueryWrapper<AdminUser>().eq(AdminUser::getEmail, email));
            if (sysUser != null) {
                // TODO: 新流程应验证 token 而非直接接受密码参数
                model.addAttribute("title", "提示");
                model.addAttribute("name", "密码重置功能已升级");
                model.addAttribute("note", "请使用最新的密码重置流程");
            } else {
                model.addAttribute("title", "抱歉");
                model.addAttribute("name", "未获取到用户信息");
                model.addAttribute("note", "操作失败");
            }
        } catch (Exception e) {
            log.error("密码重置处理异常", e);
            model.addAttribute("title", "抱歉");
            model.addAttribute("name", "系统操作过程中发生错误");
            model.addAttribute("note", "操作失败");
        }
        model.addAttribute("webhost", ConfigContextHolder.domain());
        return "msg";
    }
}

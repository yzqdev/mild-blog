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
import com.site.blog.service.AdminUserService;
import com.site.blog.service.BlogConfigService;
import com.site.blog.service.MailService;
import com.site.blog.util.JwtUtil;
import com.site.blog.util.MD5Utils;
import com.site.blog.util.RequestHelper;
import com.site.blog.util.ResultGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;


/**
 * @author yanni
 * @date time 2022/6/17 20:37
 * @modified By:
 */
@Controller
@RequestMapping("/v2/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AdminUserService adminUserService;
    private final MailService mailService;

    private final BlogConfigService blogConfigService;

    @PostMapping(value = "/login")
    @ResponseBody
    public Result login(String username, String password,
                        HttpSession session) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>(
                new AdminUser().setUsername(username)
                        .setPassword(MD5Utils.MD5Encode(password, "UTF-8"))
        );
        AdminUser adminUser = adminUserService.getOne(queryWrapper);
        if (adminUser != null) {
            if (!adminUser.getLocked()) {
                String token = JwtUtil.sign(adminUser.getUsername(), adminUser.getId());

                session.setAttribute(SessionConstants.LOGIN_USER, adminUser.getNickname());
                session.setAttribute(SessionConstants.LOGIN_USER_ID, adminUser.getId());
                session.setAttribute(SessionConstants.LOGIN_USER_NAME, adminUser.getUsername());
                session.setAttribute(SessionConstants.AUTHOR_IMG, blogConfigService.getById(
                        SysConfigConstants.SYS_AUTHOR_IMG.getConfigField()));

                return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, token);
            } else {
                return ResultGenerator.getResultByHttp(HttpStatusEnum.UNAUTHORIZED, false, "??????????????????");
            }
        } else {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.UNAUTHORIZED);
        }
    }

    /**
     * ??????html????????????
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
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST, false, "??????????????????");
        } else {
            AdminUser regUser = AdminUser.builder().username(username).password(password).uuid(UUID.fastUUID().toString()).locked(true).role(0).build();

            adminUserService.register(regUser);

            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, "????????????");

        }
    }

    @PostMapping("/findPassByMail/{email}")
    @ResponseBody
    public Result findPassEmail(@PathVariable("email") String email) {
        mailService.sendFindPassEmail(email, mailService.getDefaultMail());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, "???????????????");
    }

    @PostMapping("/findPass")
    @ResponseBody
    public Result sengFindPassEmail() {
        var user = RequestHelper.getSessionUser();
        mailService.sendFindPassEmail(user.getEmail(), mailService.getDefaultMail());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, user);
    }

    @GetMapping("/findPass")
    public String findPass(Model model, @RequestParam("email") String email, @RequestParam("cip") String cip) {
        try {

            var sysUser = adminUserService.getOne(new LambdaQueryWrapper<AdminUser>().eq(AdminUser::getEmail, email));
            var userFlag = Optional.ofNullable(sysUser);
            if (userFlag.isPresent()) {
                String newPass = HexUtil.decodeHexStr(cip);
                sysUser.setPassword(newPass);


                Boolean r = adminUserService.updateUserInfo(sysUser);
                model.addAttribute("title", "??????");
                model.addAttribute("name", "?????????:" + newPass);//
                model.addAttribute("note", "?????????????????????????????????????????????????????????");
            } else {
                model.addAttribute("title", "??????");
                model.addAttribute("name", "????????????????????????");
                model.addAttribute("note", "????????????");


            }


        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("title", "??????");
            model.addAttribute("name", "?????????????????????????????????");
            model.addAttribute("note", "????????????");
        }
        model.addAttribute("webhost", ConfigContextHolder.domain());
        return "msg";

    }
}

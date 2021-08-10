package com.site.blog.controller.v2;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.constants.SessionConstants;
import com.site.blog.constants.SysConfigConstants;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.AdminUser;
import com.site.blog.service.*;
import com.site.blog.util.MD5Utils;
import com.site.blog.util.ResultGenerator;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author Yangzhengqian
 * @description
 * @date:Created time 2021/7/9 13:43
 * @modified By:
 */
@RestController
@RequestMapping("/v2")
public class AdminV2Controller {
    @Resource
    private AdminUserService adminUserService;
    @Resource
    private BlogInfoService blogInfoService;
    @Resource
    private BlogTagService blogTagService;
    @Resource
    private BlogCategoryService blogCategoryService;
    @Resource
    private BlogCommentService blogCommentService;
    @Resource
    private BlogConfigService blogConfigService;
    @Resource
    private BlogLinkService blogLinkService;
    @ResponseBody
    @PostMapping(value = "/admin/loginPost")
    public Result<String> login(String username, String password,
                                HttpSession session) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<AdminUser>(
                new AdminUser().setLoginUserName(username)
                        .setLoginPassword(MD5Utils.MD5Encode(password,"UTF-8"))
        );
        System.out.println(MD5Utils.MD5Encode(password,"UTF-8"));
        AdminUser adminUser = adminUserService.getOne(queryWrapper);
        if (adminUser != null) {
            session.setAttribute(SessionConstants.LOGIN_USER, adminUser.getNickName());
            session.setAttribute(SessionConstants.LOGIN_USER_ID, adminUser.getAdminUserId());
            session.setAttribute(SessionConstants.LOGIN_USER_NAME, adminUser.getLoginUserName());
            session.setAttribute(SessionConstants.AUTHOR_IMG, blogConfigService.getById(
                    SysConfigConstants.SYS_AUTHOR_IMG.getConfigField()));
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,"/admin/v1/index");
        } else {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.UNAUTHORIZED);
        }
    }
    @ResponseBody
    @PostMapping(value = "/admin/regPost")
    public Result<String> register(String username, String password ) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<AdminUser>(
                new AdminUser().setLoginUserName(username)
                        .setLoginPassword(MD5Utils.MD5Encode(password,"UTF-8"))
        );
        AdminUser adminUser = adminUserService.getOne(queryWrapper);
        if (adminUser != null) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST,"用户名已存在");
        } else {
            AdminUser regUser=new AdminUser();
            regUser.setLoginUserName(username);
            regUser.setLoginPassword(password);
            adminUserService.register(regUser);

            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,"/admin/v1/index");

        }
    }

    /**
     * @Description: 验证密码是否正确
     * @Param: [oldPwd, session]
     * @return: com.zhulin.blog.dto.Result
     * @date: 2019/8/25 9:15
     */
    @ResponseBody
    @GetMapping("/admin/password")
    public Result<String> validatePassword(String oldPwd, HttpSession session){
        Integer userId = (Integer) session.getAttribute(SessionConstants.LOGIN_USER_ID);
        boolean flag = adminUserService.validatePassword(userId,oldPwd);
        if (flag){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
    }
}

package com.site.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.site.blog.constants.BaseConstants;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.constants.SessionConstants;
import com.site.blog.constants.SysConfigConstants;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.AdminUser;
import com.site.blog.service.*;
import com.site.blog.util.JwtUtil;
import com.site.blog.util.MD5Utils;
import com.site.blog.util.RequestHelper;
import com.site.blog.util.ResultGenerator;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * @author Yangzhengqian
 * @description
 * @date:Created time 2021/7/9 13:43
 * @modified By:
 */
@RestController
@RequestMapping("/v2/admin")
@Api(value = "后台json", tags = "后台json")
@Slf4j
public class AdminController {
    @Resource
    private AdminUserService adminUserService;
    @Resource
    private BlogInfoService blogInfoService;
    @Resource
    private TagService tagService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private CommentService commentService;
    @Resource
    private BlogConfigService blogConfigService;
    @Resource
    private LinkService linkService;

    @PostMapping(value = "/login")
    public Result login(String username, String password,
                        HttpSession session) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<AdminUser>(
                new AdminUser().setLoginUserName(username)
                        .setLoginPassword(MD5Utils.MD5Encode(password, "UTF-8"))
        );
        System.out.println(MD5Utils.MD5Encode(password, "UTF-8"));
        AdminUser adminUser = adminUserService.getOne(queryWrapper);
        if (adminUser != null) {
            if (adminUser.getLocked()==0){
                String token = JwtUtil.sign(adminUser.getLoginUserName(), adminUser.getAdminUserId());

                session.setAttribute(SessionConstants.LOGIN_USER, adminUser.getNickName());
                session.setAttribute(SessionConstants.LOGIN_USER_ID, adminUser.getAdminUserId());
                session.setAttribute(SessionConstants.LOGIN_USER_NAME, adminUser.getLoginUserName());
                session.setAttribute(SessionConstants.AUTHOR_IMG, blogConfigService.getById(
                        SysConfigConstants.SYS_AUTHOR_IMG.getConfigField()));

                return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,true, token);
            }else {
                return  ResultGenerator.getResultByHttp(HttpStatusEnum.UNAUTHORIZED,false,"账户已被冻结");
            }
        } else {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.UNAUTHORIZED);
        }
    }

    @PostMapping("/del/{id}")
    public Result removeUser(@PathVariable("id") Integer id) {
        if (id == null) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST, "请输入id");
        } else {
            boolean flag = adminUserService.removeById(id);

            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, flag);
        }
    }

    @PostMapping(value = "/reg")
    public Result<String> register(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<AdminUser>(
                new AdminUser().setLoginUserName(username)
                        .setLoginPassword(MD5Utils.MD5Encode(password, "UTF-8"))
        );
        AdminUser adminUser = adminUserService.getOne(queryWrapper);
        if (adminUser != null) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST, "用户名已存在");
        } else {
            AdminUser regUser = new AdminUser();
            regUser.setLoginUserName(username);
            regUser.setLoginPassword(password);
            regUser.setLocked(1);
            regUser.setRole(0);
            adminUserService.register(regUser);

            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, "/admin/v1/index");

        }
    }

    @GetMapping("/users")
    public Result getUsers() {

        List<AdminUser> users = adminUserService.list();
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, users);
    }

    /**
     * @Description: 验证密码是否正确
     * @Param: [oldPwd, session]
     * @return: com.zhulin.blog.dto.Result
     * @date: 2019/8/25 9:15
     */
    @GetMapping("/password")
    public Result<String> validatePassword(String oldPwd, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(SessionConstants.LOGIN_USER_ID);
        boolean flag = adminUserService.validatePassword(userId, oldPwd);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
    }

    @PostMapping("/unlock/{id}")
    public Result unlock(@PathVariable("id") String id) {
        AdminUser user = adminUserService.getAdminUserById(Integer.valueOf(id));
        AdminUser currentUser= RequestHelper.getSessionUser();
        if (user.getAdminUserId().equals(currentUser.getAdminUserId())){
            return  ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR,"不能冻结自己");
        }
        if (user.getLocked()==0){
            user.setLocked(1);
        }else{
            user.setLocked(0);
        }
        adminUserService.updateById(user);
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, user.getLocked());
    }

    @GetMapping("/getUser")
    public Result getUserInfo(HttpServletRequest request) {
        try {
            AdminUser user = (AdminUser) request.getAttribute(BaseConstants.USER_ATTR);

            System.out.println("心如这里");
            HashMap<String, Object> res = new HashMap<>(1);
            if (user==null){
                return  ResultGenerator.getResultByHttp(HttpStatusEnum.UNAUTHORIZED,false,"请重新登录");
            }
            res.put("user", user);
            System.out.println("user="+user);
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,true, res);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR, "fail");
    }
}

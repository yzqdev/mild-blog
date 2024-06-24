package com.site.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.site.blog.aop.LogOperationEnum;
import com.site.blog.aop.SysLogAnnotation;
import com.site.blog.constants.BaseConstants;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.constants.SessionConstants;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.AdminUser;
import com.site.blog.model.entity.BlogConfig;
import com.site.blog.model.vo.UserVo;
import com.site.blog.service.*;
import com.site.blog.util.RequestHelper;
import com.site.blog.util.ResultGenerator;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Yangzhengqian
 * @description
 * @date:Created time 2021/7/9 13:43
 * @modified By:
 */
@RestController
@RequestMapping("/v2/admin")
@Tag(name = "后台json", description = "后台json")
@Slf4j
@RequiredArgsConstructor
public class AdminController {
     
    private final AdminUserService adminUserService;
     
    private final BlogInfoService blogInfoService;
     
    private final TagService tagService;
     
    private final CategoryService categoryService;
     
    private final CommentService commentService;
     
    private final BlogConfigService blogConfigService;
     
    private final LinkService linkService;

    @SysLogAnnotation(title = "删除用户",opType = LogOperationEnum.DELETE)
    @PostMapping("/del/{id}")
    public Result removeUser(@PathVariable("id") Integer id) {
        if (id == null) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST, "请输入id");
        } else {
            boolean flag = adminUserService.removeById(id);

            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, flag);
        }
    }
    @GetMapping("/dashboard")
    public  Result dashboard(){
      var res=new HashMap<String,Object>();
        var articleCount=blogInfoService.count();
        var commentCount=commentService.count();
        var views=blogInfoService.getViewsSum();
        res.put("articleCount",articleCount);
        res.put("commentCount",commentCount);
        res.put("viewsCount",views);
        return  ResultGenerator.getResultByHttp(HttpStatusEnum.OK,true,res);
    }
    @SysLogAnnotation(title = "更改用户",opType = LogOperationEnum.EDIT)
    @PostMapping("/userEdit")
    public  Result editUser(UserVo userVo){
        AdminUser sqlUser=adminUserService.getAdminUserById(userVo.getId());
        BeanUtils.copyProperties(userVo,sqlUser);
        var conf=blogConfigService.getOne(new LambdaQueryWrapper<BlogConfig>().eq(BlogConfig::getConfigCode,"sysAuthorImg"));
        conf.setConfigValue(userVo.getAvatar());
        blogConfigService.updateById(conf);
        adminUserService.updateUserInfo(sqlUser);


        return  ResultGenerator.getResultByHttp(HttpStatusEnum.OK,true,sqlUser);
    }


    @GetMapping("/users")
    public Result getUsers() {

       var users = adminUserService.list();
       var userVos=new ArrayList<UserVo>();
       users.forEach(item->{
           var userVoItem=new UserVo();
           BeanUtils.copyProperties(item,userVoItem);
           userVos.add(userVoItem);
       });
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,true, userVos);
    }

    /**
     * @Description: 验证密码是否正确
     * @Param: [oldPwd, session]
     * @return: com.zhulin.blog.dto.Result
     * @date: 2019/8/25 9:15
     */
    @GetMapping("/password")
    public Result<String> validatePassword(String oldPwd, HttpSession session) {
        AdminUser user= (AdminUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = user.getId();
        boolean flag = adminUserService.validatePassword(userId, oldPwd);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
    }
    @SysLogAnnotation(title = "冻结用户",opType = LogOperationEnum.CHANGE_STATUS)
    @PostMapping("/unlock/{id}")
    public Result unlock(@PathVariable("id") String id) {
        AdminUser user = adminUserService.getAdminUserById( id);
        UserVo currentUser = RequestHelper.getSessionUser();
        log.info(currentUser.toString());
        if (user.getId().equals(currentUser.getId())) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR, "不能冻结自己");
        }
        user.setLocked(!user.getLocked());
        adminUserService.updateById(user);
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, user.getLocked()?"冻结":"未冻结");
    }

    @GetMapping("/getUser")
    public Result getUserInfo(HttpServletRequest request) {
        try {
           UserVo user = (UserVo) request.getAttribute(BaseConstants.USER_ATTR);



            if (user == null) {
                return ResultGenerator.getResultByHttp(HttpStatusEnum.UNAUTHORIZED, false, "请重新登录");
            }

           log.info(user.toString());
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR, false, e.getMessage());
        }

    }
}

package com.site.blog.context;

import com.site.blog.constants.BaseConstants;
import com.site.blog.mapper.AdminUserMapper;
import com.site.blog.model.entity.AdminUser;
import com.site.blog.model.vo.UserVo;
import com.site.blog.util.JwtUtil;
import com.site.blog.util.RequestHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yanni
 * @date time 2022/6/18 3:50
 * @modified By:
 */

public interface UserContext {
    UserVo getUser();

}

package com.site.blog.context;

import com.site.blog.constants.BaseConstants;
import com.site.blog.mapper.AdminUserMapper;
import com.site.blog.model.vo.UserVo;
import com.site.blog.util.JwtService;
import com.site.blog.util.RequestHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author yanni
 * @date time 2022/6/18 3:57
 * @modified By:
 */
@Service
public class UserContextService implements  UserContext{

    private  final   AdminUserMapper adminUserMapper;

    public UserContextService(AdminUserMapper adminUserMapper) {
        this.adminUserMapper = adminUserMapper;
    }

    /* (non-Javadoc)
     * @see com.site.blog.context.UserContext#getUser()
     */
    @Override
    public UserVo getUser(){
        var token= RequestHelper.getRequestHeader(BaseConstants.TOKEN);
        var user=adminUserMapper.selectById(JwtService.getUserId(token));
        var userVo=new UserVo();
        BeanUtils.copyProperties(user,userVo);
        return  userVo;
    }
}

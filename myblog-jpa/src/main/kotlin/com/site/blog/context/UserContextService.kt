package com.site.blog.context

import com.site.blog.constants.BaseConstants
import com.site.blog.mapper.AdminUserMapper
import com.site.blog.model.vo.UserVo
import com.site.blog.util.JwtUtil.getUserId
import com.site.blog.util.RequestHelper.getRequestHeader
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service
import jakarta.annotation.Resource

/**
 * @author yanni
 * @date time 2022/6/18 3:57
 * @modified By:
 */
@Service
class UserContextService : UserContext {
    @Resource
    private val adminUserMapper: AdminUserMapper? = null

    /* (non-Javadoc)
     * @see com.site.blog.context.UserContext#getUser()
     */
    override fun user(): UserVo {
        val token = getRequestHeader(BaseConstants.TOKEN)
        val user = adminUserMapper!!.selectById(getUserId(token))
        val userVo = UserVo()
        if (user != null) {
            BeanUtils.copyProperties(user, userVo)
        }
        return userVo
    }
}
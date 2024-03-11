package com.site.blog.context

import com.site.blog.model.vo.UserVo

/**
 * @author yanni
 * @date time 2022/6/18 3:50
 * @modified By:
 */
interface UserContext {
    fun user(): UserVo?
}
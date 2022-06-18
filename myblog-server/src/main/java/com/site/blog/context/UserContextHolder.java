package com.site.blog.context;

import cn.hutool.extra.spring.SpringUtil;


/**
 * @author yanni
 * @date time 2022/6/18 3:50
 * @modified By:
 */
public class UserContextHolder {
    public static UserContext me() {
        return SpringUtil.getBean(UserContext.class);
    }

}

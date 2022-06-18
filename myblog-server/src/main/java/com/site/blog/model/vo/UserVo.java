package com.site.blog.model.vo;

import lombok.Data;

/**
 * @author yanni
 * @date time 2022/6/15 15:47
 * @modified By:
 */
@Data
public class UserVo {
    private String id;

    /**
     * 管理员登陆名称
     */
    private String username;




    /**
     * 管理员显示昵称
     */
    private String nickname;

    /**
     * 是否锁定 0未锁定 1已锁定无法登陆
     */
    private Boolean locked;
    /**
     * 0  普通用户,1管理员
     */
    private Integer role;
    private String avatar;
    private String email;
    private String uuid;
}

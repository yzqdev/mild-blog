package com.site.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serial;
import java.io.Serializable;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 后台管理员信息表
 * </p>
 *
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AdminUser extends Model<AdminUser> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    @TableId(  type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 管理员登陆名称
     */
    @TableField("username")
    private String username;

    /**
     * 管理员登陆密码
     */
    @TableField("password")
    private String password;

    /**
     * 管理员显示昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 是否锁定 0未锁定 1已锁定无法登陆
     */
    @TableField("locked")
    private Boolean locked;
    private String uuid;
    /**
     * 0  普通用户,1管理员
     */
    private Integer role;
    private String avatar;
    private  String email;
}

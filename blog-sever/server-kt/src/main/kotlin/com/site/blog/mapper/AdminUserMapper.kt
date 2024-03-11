package com.site.blog.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.site.blog.model.entity.AdminUser
import org.apache.ibatis.annotations.Mapper

/**
 *
 *
 * 后台管理员信息表 Mapper 接口
 *
 *
 * @author zhulin
 * @since 2019-08-25
 */
@Mapper
interface AdminUserMapper : BaseMapper<AdminUser?>
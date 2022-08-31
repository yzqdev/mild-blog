package com.site.blog.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.site.blog.model.entity.EmailConfig
import org.apache.ibatis.annotations.Mapper

/**
 * @author yanni
 * @date time 2022/6/18 1:25
 * @modified By:
 */
@Mapper
interface EmailMapper : BaseMapper<EmailConfig?>
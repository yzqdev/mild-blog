package com.site.blog.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.site.blog.model.entity.Tag
import org.apache.ibatis.annotations.Mapper

/**
 *
 *
 * 标签表 Mapper 接口
 *
 *
 * @author zhulin
 * @since 2019-08-28
 */
@Mapper
interface TagMapper : BaseMapper<Tag?>
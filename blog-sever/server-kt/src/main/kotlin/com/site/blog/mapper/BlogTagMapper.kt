package com.site.blog.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.site.blog.model.entity.BlogTag
import org.apache.ibatis.annotations.Mapper

/**
 *
 *
 * 博客跟标签的关系表 Mapper 接口
 *
 *
 * @author zhulin
 * @since 2019-08-28
 */
@Mapper
interface BlogTagMapper : BaseMapper<BlogTag?>
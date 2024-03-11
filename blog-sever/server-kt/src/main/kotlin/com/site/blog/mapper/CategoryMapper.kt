package com.site.blog.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.site.blog.model.entity.Category
import org.apache.ibatis.annotations.Mapper

/**
 *
 *
 * 博客分类 Mapper 接口
 *
 *
 * @author zhulin
 * @since 2019-08-30
 */
@Mapper
interface CategoryMapper : BaseMapper<Category?>
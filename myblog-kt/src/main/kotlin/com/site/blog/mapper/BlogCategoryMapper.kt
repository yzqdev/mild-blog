package com.site.blog.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.site.blog.model.entity.BlogCategory
import org.apache.ibatis.annotations.Mapper

/**
 * @author yzq
 * @description
 * @date:Created time 2021/8/25 23:59
 * @modified By:
 */
@Mapper
interface BlogCategoryMapper : BaseMapper<BlogCategory?>
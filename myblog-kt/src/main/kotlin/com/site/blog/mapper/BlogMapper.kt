package com.site.blog.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.site.blog.model.entity.BlogInfo
import org.apache.ibatis.annotations.Mapper

@Mapper
interface BlogMapper : BaseMapper<BlogInfo?>
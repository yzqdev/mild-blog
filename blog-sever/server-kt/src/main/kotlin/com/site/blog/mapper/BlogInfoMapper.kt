package com.site.blog.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.site.blog.model.entity.BlogInfo
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

/**
 *
 *
 * 博客信息表 Mapper 接口
 *
 *
 * @author zhulin
 * @since 2019-08-27
 */
@Mapper
interface BlogInfoMapper : BaseMapper<BlogInfo?> {
    /**
     * 获取
     *
     * @return [Integer]
     */
    @Select("select sum(blog_views) FROM  blog_info")
    fun getViews(): Int?
}
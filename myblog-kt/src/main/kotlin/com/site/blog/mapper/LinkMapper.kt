package com.site.blog.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.site.blog.model.entity.Link
import org.apache.ibatis.annotations.Mapper

/**
 *
 *
 * 友情链接表 Mapper 接口
 *
 *
 * @author zhulin
 * @since 2019-09-02
 */
@Mapper interface LinkMapper : BaseMapper<Link?>
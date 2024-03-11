package com.site.blog.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.site.blog.model.entity.Comment
import org.apache.ibatis.annotations.Mapper

/**
 *
 *
 * 评论信息表 Mapper 接口
 *
 *
 * @author zhulin
 * @since 2019-09-04
 */
@Mapper
interface CommentMapper : BaseMapper<Comment?>
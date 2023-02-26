package com.site.blog.service.impl


import com.site.blog.mapper.CommentMapper
import com.site.blog.model.entity.Comment
import com.site.blog.service.CommentService
import org.springframework.stereotype.Service

/**
 *
 *
 * 评论信息表 服务实现类
 *
 *
 */
@Service
class CommentServiceImpl : ServiceImpl<CommentMapper?, Comment?>(), CommentService
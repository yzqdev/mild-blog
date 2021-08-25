package com.site.blog.service.impl;

import com.site.blog.model.entity.Comment;
import com.site.blog.mapper.CommentMapper;
import com.site.blog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论信息表 服务实现类
 * </p>
 *
 * @author: 南街
 * @since 2019-09-04
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}

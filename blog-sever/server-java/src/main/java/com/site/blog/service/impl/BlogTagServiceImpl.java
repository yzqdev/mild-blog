package com.site.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.site.blog.model.entity.BlogInfo;
import com.site.blog.model.entity.BlogTag;
import com.site.blog.mapper.BlogTagMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.service.BlogTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 博客跟标签的关系表 服务实现类
 * </p>
 *
 */
@Service
@RequiredArgsConstructor
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTag> implements BlogTagService {


    private final BlogTagMapper blogTagMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeAndsaveBatch(List<String> blogTagIds, BlogInfo blogInfo) {
      String blogId = blogInfo.getBlogId();
        List<BlogTag> list = blogTagIds.stream().map(blogTagId -> new BlogTag()
                .setTagId( blogTagId )
                .setBlogId(blogId)).toList();
        blogTagMapper.delete(new QueryWrapper<BlogTag>()
                .lambda()
                .eq(BlogTag::getBlogId, blogInfo.getBlogId()));
        for (BlogTag item : list) {
            blogTagMapper.insert(item);
        }
    }
}

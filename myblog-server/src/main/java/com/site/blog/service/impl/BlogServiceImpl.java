package com.site.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.site.blog.model.entity.BlogInfo;
import com.site.blog.model.entity.BlogTag;
import com.site.blog.mapper.BlogTagMapper;
import com.site.blog.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 博客跟标签的关系表 服务实现类
 * </p>
 *
 * @author: 南街
 * @since 2019-08-28
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogTagMapper, BlogTag> implements BlogService {

    @Resource
    private BlogTagMapper blogTagMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeAndsaveBatch(List<String> blogTagIds, BlogInfo blogInfo) {
        Long blogId = blogInfo.getBlogId();
        List<BlogTag> list = blogTagIds.stream().map(blogTagId -> new BlogTag()
                .setTagId(Integer.valueOf(blogTagId))
                .setBlogId(blogId)).collect(Collectors.toList());
        blogTagMapper.delete(new QueryWrapper<BlogTag>()
                .lambda()
                .eq(BlogTag::getBlogId, blogInfo.getBlogId()));
        for (BlogTag item : list) {
            blogTagMapper.insert(item);
        }
    }
}

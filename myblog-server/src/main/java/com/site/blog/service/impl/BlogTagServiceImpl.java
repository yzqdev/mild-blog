package com.site.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.mapper.BlogCategoryMapper;
import com.site.blog.mapper.BlogTagMapper;
import com.site.blog.model.entity.BlogCategory;
import com.site.blog.model.entity.BlogTag;
import com.site.blog.service.BlogTagService;
import org.springframework.stereotype.Service;

/**
 * @author yzq
 * @description
 * @date:Created time 2021/8/26 0:00
 * @modified By:
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTag> implements BlogTagService {
}

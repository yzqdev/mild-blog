package com.site.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.mapper.BlogCategoryMapper;
import com.site.blog.model.entity.BlogCategory;
import com.site.blog.service.BlogCategoryService;
import org.springframework.stereotype.Service;

/**
 * @author Yangzhengqian
 * @description
 * @date:Created time 2021/8/26 11:24
 * @modified By:
 */
@Service
public class BlogCategoryServiceImpl extends ServiceImpl<BlogCategoryMapper, BlogCategory> implements BlogCategoryService {
}

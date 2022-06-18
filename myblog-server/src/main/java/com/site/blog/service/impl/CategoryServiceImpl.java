package com.site.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.constants.SysConfigConstants;
import com.site.blog.service.BlogCategoryService;
import com.site.blog.mapper.CategoryMapper;
import com.site.blog.model.entity.BlogCategory;
import com.site.blog.model.entity.Category;
import com.site.blog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 博客分类 服务实现类
 * </p>
 *
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


  private final   CategoryMapper categoryMapper;

    private  final BlogCategoryService blogCategoryService;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean clearCategory(String id) {
        Category category = categoryMapper.selectById(id);
        BlogCategory blogCategory = new BlogCategory();
                blogCategory.setCategoryId( SysConfigConstants.DEFAULT_CATEGORY.getConfigField()  );

        LambdaUpdateWrapper<BlogCategory> updateWrapper = Wrappers.<BlogCategory>lambdaUpdate()
                .eq(BlogCategory::getCategoryId, category.getCategoryId());
        blogCategoryService.update(blogCategory, updateWrapper);
        return baseMapper.deleteById(category.getCategoryId())==1;
    }
}

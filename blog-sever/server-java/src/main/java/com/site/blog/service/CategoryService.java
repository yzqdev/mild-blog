package com.site.blog.service;

import com.site.blog.model.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 博客分类 服务类
 * </p>
 *
 */
public interface CategoryService extends IService<Category> {

    public boolean clearCategory(String id);

}

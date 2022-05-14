package com.site.blog.service;

import com.site.blog.model.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 博客分类 服务类
 * </p>
 *
 * @author: 南街
 * @since 2019-08-30
 */
public interface CategoryService extends IService<Category> {

    public boolean clearCategory(String id);

}

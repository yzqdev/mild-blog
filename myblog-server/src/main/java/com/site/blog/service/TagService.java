package com.site.blog.service;

import com.site.blog.model.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.site.blog.model.entity.BlogTagCount;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author: 南街
 * @since 2019-08-28
 */
public interface TagService extends IService<Tag> {

    List<BlogTagCount> getBlogTagCountForIndex();

    boolean clearTag(Integer tagId);

}

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
 */
public interface TagService extends IService<Tag> {

    /**
     * 让博客标签数指数
     *
     * @return {@link List}<{@link BlogTagCount}>
     */
    List<BlogTagCount> getBlogTagCountForIndex();

    /**
     * 清除标签
     *
     * @param tagId 标签id
     * @return boolean
     */
    int clearTag(String tagId);

}

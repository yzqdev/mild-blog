package com.site.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.site.blog.model.entity.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author yzq
 * @description
 * @date:Created time 2021/8/26 0:00
 * @modified By:
 */
public interface BlogTagService extends IService<BlogTag> {
    public void removeAndsaveBatch(List<String> blogTagIds, BlogInfo blogInfo);
}

package com.site.blog.service;

import com.site.blog.model.entity.BlogConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
public interface BlogConfigService extends IService<BlogConfig> {

    Map<String, String> getAllConfigs();

}

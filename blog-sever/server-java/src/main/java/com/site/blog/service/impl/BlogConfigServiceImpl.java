package com.site.blog.service.impl;

import com.site.blog.service.BlogConfigService;
import com.site.blog.model.entity.BlogConfig;
import com.site.blog.mapper.BlogConfigMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 */
@Service
@RequiredArgsConstructor
public class BlogConfigServiceImpl extends ServiceImpl<BlogConfigMapper, BlogConfig> implements BlogConfigService {


    private final BlogConfigMapper blogConfigMapper;


    @Override
    public Map<String, String> getAllConfigs() {
        List<BlogConfig> list = blogConfigMapper.selectList(null);
        return list.stream().collect(Collectors.toMap(
                BlogConfig::getConfigCode,BlogConfig::getConfigValue
        ));
    }
}

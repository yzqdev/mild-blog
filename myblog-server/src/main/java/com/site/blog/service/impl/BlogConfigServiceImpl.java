package com.site.blog.service.impl;

import com.site.blog.model.entity.BlogConfig;
import com.site.blog.mapper.BlogConfigMapper;
import com.site.blog.service.BlogConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author: 南街
 * @since 2019-08-30
 */
@Service
public class BlogConfigServiceImpl extends ServiceImpl<BlogConfigMapper, BlogConfig> implements BlogConfigService {

    @Resource
    private BlogConfigMapper blogConfigMapper;


    @Override
    public Map<String, String> getAllConfigs() {
        List<BlogConfig> list = blogConfigMapper.selectList(null);
        return list.stream().collect(Collectors.toMap(
                BlogConfig::getConfigField,BlogConfig::getConfigValue
        ));
    }
}

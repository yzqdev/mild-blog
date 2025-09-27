package com.site.blog.service.impl;

import cn.hutool.log.StaticLog;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.site.blog.config.listener.SqlConstant;
import com.site.blog.mapper.AdminUserMapper;
import com.site.blog.mapper.BlogConfigMapper;
import com.site.blog.mapper.CategoryMapper;
import com.site.blog.mapper.TagMapper;
import com.site.blog.model.entity.AdminUser;
import com.site.blog.model.entity.BlogConfig;
import com.site.blog.model.entity.Category;
import com.site.blog.model.entity.Tag;
import com.site.blog.service.InitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author yanni
 * @date time 2022/6/15 23:03
 * @modified By:
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class InitServiceImpl implements InitService {
    private final AdminUserMapper adminUserMapper;
    private final BlogConfigMapper blogConfigMapper;
    private final TagMapper tagMapper;
    private final CategoryMapper categoryMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void initDatabase() {
        boolean flag = blogConfigMapper.exists(new LambdaQueryWrapper<BlogConfig>().eq(BlogConfig::getConfigCode, "init"));
        if (!flag) {

            StaticLog.warn("创建sql完成");
        }

    }

    @Override
    public void initUseEntity() {
      try {
          boolean flag = blogConfigMapper.exists(new LambdaQueryWrapper<BlogConfig>().eq(BlogConfig::getConfigCode, "init"));
          if (!flag) {
 
              StaticLog.warn("创建sql完成");
          }
      } catch (Exception e) {
          log.error(e.getMessage(),e);
      }
    }
    private void initConf(){

    }
}

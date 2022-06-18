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
public class InitServiceImpl implements InitService {
    private final AdminUserMapper adminUserMapper;
    private final BlogConfigMapper blogConfigMapper;
    private final TagMapper tagMapper;
    private final CategoryMapper categoryMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void initDatabase() {
        boolean flag = blogConfigMapper.exists(new LambdaQueryWrapper<BlogConfig>().eq(BlogConfig::getCode, "init"));
        if (!flag) {
            jdbcTemplate.execute(SqlConstant.initAdminSql);
            jdbcTemplate.execute(SqlConstant.initTagSql);
            jdbcTemplate.execute(SqlConstant.initCateSql);
            jdbcTemplate.execute(SqlConstant.initLinkSql);
            jdbcTemplate.execute(SqlConstant.insertConfigDataSql);
            jdbcTemplate.execute(SqlConstant.initSysDictTypeSql);
            jdbcTemplate.execute(SqlConstant.initSysDictDataSql);
            StaticLog.warn("创建sql完成");
        }

    }

    @Override
    public void initUseEntity() {
        boolean flag = blogConfigMapper.exists(new LambdaQueryWrapper<BlogConfig>().eq(BlogConfig::getCode, "init"));
        if (!flag) {

            adminUserMapper.insert(AdminUser.builder().id("myid").username("admin").password("e10adc3949ba59abbe56e057f20f883e").nickname("管理员").locked(false).role(1).avatar("https://img-static.mihoyo.com/communityweb/upload/222b847170feb3f2babcc1bd4f0e30dd.png").build());
            var tag = Tag.builder().tagId("1").tagName("默认tag").createTime(LocalDateTime.now()).updateTime(LocalDateTime.now()).show(true).build();
            var category= Category.builder().categoryId("1").categoryName("默认分类").createTime(LocalDateTime.now()).updateTime(LocalDateTime.now()).categoryRank(1).build();
            tagMapper.insert(tag);
           categoryMapper.insert(category);
            jdbcTemplate.execute(SqlConstant.initLinkSql);
            jdbcTemplate.execute(SqlConstant.insertConfigDataSql);
            jdbcTemplate.execute(SqlConstant.initSysDictTypeSql);
            jdbcTemplate.execute(SqlConstant.initSysDictDataSql);
            StaticLog.warn("创建sql完成");
        }
    }
    private void initConf(){

    }
}

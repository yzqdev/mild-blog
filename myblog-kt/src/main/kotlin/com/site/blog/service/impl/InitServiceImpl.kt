package com.site.blog.service.impl

import cn.hutool.log.StaticLog
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.site.blog.config.listener.SqlConstant
import com.site.blog.mapper.AdminUserMapper
import com.site.blog.mapper.BlogConfigMapper
import com.site.blog.mapper.CategoryMapper
import com.site.blog.mapper.TagMapper
import com.site.blog.model.entity.AdminUser
import com.site.blog.model.entity.BlogConfig
import com.site.blog.model.entity.Category
import com.site.blog.model.entity.Tag
import com.site.blog.service.InitService
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * @author yanni
 * @date time 2022/6/15 23:03
 * @modified By:
 */
@Service
class InitServiceImpl(
    private val adminUserMapper: AdminUserMapper,
    private val blogConfigMapper: BlogConfigMapper,
    private val tagMapper: TagMapper,
    private val categoryMapper: CategoryMapper,
    private val jdbcTemplate: JdbcTemplate
) : InitService {




    override fun initDatabase() {
        val flag: Boolean = blogConfigMapper.exists(KtQueryWrapper (BlogConfig()).eq(BlogConfig::configCode, "init"))
        if (!flag) {
            jdbcTemplate.execute(SqlConstant.initAdminSql)
            jdbcTemplate.execute(SqlConstant.initTagSql)
            jdbcTemplate.execute(SqlConstant.initCateSql)
            jdbcTemplate.execute(SqlConstant.initLinkSql)
            jdbcTemplate.execute(SqlConstant.insertConfigDataSql)
            jdbcTemplate.execute(SqlConstant.initSysDictTypeSql)
            jdbcTemplate.execute(SqlConstant.initSysDictDataSql)
            StaticLog.warn("创建sql完成")
        }
    }

    override fun initUseEntity() {
        val flag = blogConfigMapper.exists(QueryWrapper<BlogConfig>().eq("config_code","init"))
        val userLambda=blogConfigMapper.exists(KtQueryWrapper(BlogConfig()).eq(BlogConfig::configCode,"init"))
        //使用lambda结果错误
        println("使用lambda ,$userLambda")
//        val flag = blogConfigMapper.exists(KtQueryWrapper (BlogConfig()).eq(BlogConfig::code, "init"))
        println("是否初始化过? $flag")
        if (!flag ) {
            //123456
            adminUserMapper.insert(
                AdminUser(id="myid", username = "admin",password = "e10adc3949ba59abbe56e057f20f883e", nickname = "管理员", locked = false, role = 1, avatar = "https://img-static.mihoyo.com/communityweb/upload/222b847170feb3f2babcc1bd4f0e30dd.png")
//
            )
            val tag: Tag = Tag ("1","默认tag",true,LocalDateTime.now(),
                LocalDateTime.now()
             )
            val category=Category(categoryId = "1",categoryName = "默认分类", createTime = LocalDateTime.now(), updateTime = LocalDateTime.now(), categoryRank = 1)

            tagMapper.insert(tag)
            categoryMapper.insert(category)
            jdbcTemplate.execute(SqlConstant.initLinkSql)
            jdbcTemplate.execute(SqlConstant.insertConfigDataSql)
            jdbcTemplate.execute(SqlConstant.initSysDictTypeSql)
            jdbcTemplate.execute(SqlConstant.initSysDictDataSql)
            StaticLog.warn("创建sql完成")
        }
    }

    private fun initConf() {}
}
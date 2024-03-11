package com.site.blog.config.listener

import cn.hutool.core.util.ObjectUtil
import cn.hutool.db.DbUtil
import cn.hutool.db.Entity
import cn.hutool.db.handler.EntityListHandler
import cn.hutool.db.sql.SqlExecutor
import cn.hutool.log.Log
import com.site.blog.context.ConfigContext
import org.springframework.boot.context.event.ApplicationContextInitializedEvent
import org.springframework.context.ApplicationListener
import org.springframework.core.Ordered
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.function.Consumer

/**
 * 初始化常量的监听器
 *
 *
 * 当spring装配好配置后，就去数据库读constants
 *
 * 等待解决
 *
 *
 * [链接](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#features.spring-application.application-events-and-listeners)
 *
 *
 * @author yanni
 */
class ConstantsInitListener : ApplicationListener<ApplicationContextInitializedEvent>, Ordered {
    override fun getOrder(): Int {
        return Ordered.HIGHEST_PRECEDENCE
    }

    override fun onApplicationEvent(applicationContextInitializedEvent: ApplicationContextInitializedEvent) {
        val environment = applicationContextInitializedEvent.applicationContext.environment

        // 获取数据库连接配置
        val dataSourceUrl = environment.getProperty("spring.datasource.url")
        val dataSourceUsername = environment.getProperty("spring.datasource.username")
        val dataSourcePassword = environment.getProperty("spring.datasource.password")

        // 如果有为空的配置，终止执行
        if (ObjectUtil.hasEmpty(dataSourceUrl, dataSourceUsername, dataSourcePassword)) {
            try {
                throw Exception("database not cononnected")
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
        var conn: Connection? = null
        try {
            Class.forName("org.postgresql.Driver")
            assert(dataSourceUrl != null)
            conn = DriverManager.getConnection(dataSourceUrl, dataSourceUsername, dataSourcePassword)
            val entityList = SqlExecutor.query(conn, initSql, EntityListHandler())

            // 将查询到的参数配置添加到缓存
            if (ObjectUtil.isNotEmpty(entityList)) {
                entityList.forEach(Consumer { sysConfig: Entity ->
                    ConfigContext.putConstant(
                        sysConfig.getStr("code"),
                        sysConfig.getStr("value")
                    )
                })
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            log.error(">>> 读取数据库constants配置信息出错：{}", e.message)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            log.error(">>> 读取数据库constants配置信息出错：{}", e.message)
        } finally {
            DbUtil.close(conn)
        }
    }

    companion object {
        private val log = Log.get()
        private const val initSql = "select config_code,config_value from  blog_config  "
    }
}
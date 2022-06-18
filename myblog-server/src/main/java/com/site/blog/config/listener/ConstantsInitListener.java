
package com.site.blog.config.listener;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityHandler;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import cn.hutool.log.Log;

import com.site.blog.context.ConfigContext;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * 初始化常量的监听器
 * <p>
 * 当spring装配好配置后，就去数据库读constants
 * <p>等待解决</p>
 * <p>
 * <a href="https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#features.spring-application.application-events-and-listeners">链接</a>
 * </p>
 *
 * @author yanni
 */

public class ConstantsInitListener implements ApplicationListener<ApplicationContextInitializedEvent>, Ordered {

    private static final Log log = Log.get();

    private static final String initSql = "select code,value from  blog_config  ";

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public void onApplicationEvent(ApplicationContextInitializedEvent applicationContextInitializedEvent) {
        ConfigurableEnvironment environment = applicationContextInitializedEvent.getApplicationContext().getEnvironment();

        // 获取数据库连接配置
        String dataSourceUrl = environment.getProperty("spring.datasource.url");
        String dataSourceUsername = environment.getProperty("spring.datasource.username");
        String dataSourcePassword = environment.getProperty("spring.datasource.password");

        // 如果有为空的配置，终止执行
        if (ObjectUtil.hasEmpty(dataSourceUrl, dataSourceUsername, dataSourcePassword)) {
            try {
                throw new Exception("database not cononnected");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            assert dataSourceUrl != null;
            conn = DriverManager.getConnection(dataSourceUrl, dataSourceUsername, dataSourcePassword);
            List<Entity> entityList = SqlExecutor.query(conn, initSql, new EntityListHandler());

            // 将查询到的参数配置添加到缓存
            if (ObjectUtil.isNotEmpty(entityList)) {
                entityList.forEach(sysConfig -> ConfigContext.putConstant(sysConfig.getStr("code"), sysConfig.getStr("value")));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            log.error(">>> 读取数据库constants配置信息出错：{}", e.getMessage());

        } finally {
            DbUtil.close(conn);
        }

    }
}

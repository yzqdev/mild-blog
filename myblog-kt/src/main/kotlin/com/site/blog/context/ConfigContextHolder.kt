package com.site.blog.context

import cn.hutool.core.convert.Convert
import cn.hutool.core.util.ObjectUtil
import cn.hutool.core.util.StrUtil
import com.site.blog.context.ConfigContext.me
import com.site.blog.exception.ServiceException
import org.slf4j.LoggerFactory

/**
 * @author yanni
 * @date time 2022/6/17 21:07
 * @modified By:
 */

object ConfigContextHolder {
    val log = LoggerFactory.getLogger(this.javaClass)
    @JvmStatic
    fun websiteName(): String {
        return getSysConfigWithDefault("websiteName", String::class.java, "我的博客")
    }

    @JvmStatic
    fun domain(): String {
        return getSysConfigWithDefault("domain", String::class.java, "http://localhost:2801")
    }

    fun <T> getSysConfigWithDefault(configCode: String?, clazz: Class<T>?, defaultValue: T): T {
        val configValue = me().getStr(configCode)
        return if (ObjectUtil.isEmpty(configValue)) {
            // 将默认值加入到缓存常量
            ConfigContextHolder.log.warn(
                ">>> 系统配置blog_config表中存在空项，configCode为：{}，系统采用默认值：{}",
                configCode,
                defaultValue
            )
            me().put(configCode, defaultValue)
            defaultValue
        } else {
            try {
                Convert.convert(clazz, configValue)
            } catch (e: Exception) {
                defaultValue
            }
        }
    }

    fun <T> getSysConfig(configCode: String?, clazz: Class<T>?, nullThrowExp: Boolean): T? {
        ConfigContextHolder.log.info(me().toString())
        val configValue = me().getStr(configCode)
        return if (ObjectUtil.isEmpty(configValue)) {
            if (java.lang.Boolean.TRUE == nullThrowExp) {
                val format =
                    StrUtil.format(">>> getSysConfig 系统配置blog_config表中存在空项，configCode为：{}", configCode)
                ConfigContextHolder.log.error(format)
                throw ServiceException(404, format)
            } else {
                null
            }
        } else {
            try {
                Convert.convert(clazz, configValue)
            } catch (e: Exception) {
                if (java.lang.Boolean.TRUE == nullThrowExp) {
                    val format = StrUtil.format(
                        ">>> 系统配置blog_config表中存在格式错误的值，configCode={}，configValue={}",
                        configCode,
                        configValue
                    )
                    ConfigContextHolder.log.error(format)
                    throw ServiceException(404, format)
                } else {
                    null
                }
            }
        }
    }
}
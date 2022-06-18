package com.site.blog.context;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.site.blog.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yanni
 * @date time 2022/6/17 21:07
 * @modified By:
 */
@Slf4j
public class ConfigContextHolder {

    public static String websiteName() {
        return getSysConfigWithDefault("websiteName", String.class, "我的博客");
    }

    public static String domain() {
        return getSysConfigWithDefault("domain", String.class, "http://localhost:2801");
    }
    public static <T> T getSysConfigWithDefault(String configCode, Class<T> clazz, T defaultValue) {
        String configValue = ConfigContext.me().getStr(configCode);
        if (ObjectUtil.isEmpty(configValue)) {
            // 将默认值加入到缓存常量
            log.warn(">>> 系统配置blog_config表中存在空项，configCode为：{}，系统采用默认值：{}", configCode, defaultValue);
            ConfigContext.me().put(configCode, defaultValue);
            return defaultValue;
        } else {
            try {
                return Convert.convert(clazz, configValue);
            } catch (Exception e) {
                return defaultValue;
            }
        }
    }
    public static <T> T getSysConfig(String configCode, Class<T> clazz, Boolean nullThrowExp) {
        log.info(ConfigContext.me().toString());

        String configValue = ConfigContext.me().getStr(configCode);
        if (ObjectUtil.isEmpty(configValue)) {
            if (Boolean.TRUE.equals(nullThrowExp)) {
                String format = StrUtil.format(">>> getSysConfig 系统配置blog_config表中存在空项，configCode为：{}", configCode);
                log.error(format);
                throw new ServiceException(404, format);
            } else {
                return null;
            }
        } else {
            try {
                return Convert.convert(clazz, configValue);
            } catch (Exception e) {
                if (Boolean.TRUE.equals(nullThrowExp)) {
                    String format = StrUtil.format(">>> 系统配置blog_config表中存在格式错误的值，configCode={}，configValue={}", configCode, configValue);
                    log.error(format);
                    throw new ServiceException(404, format);
                } else {
                    return null;
                }
            }
        }
    }
}

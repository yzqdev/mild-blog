package com.site.blog.constants;


public enum SysConfigConstants {
    /**
     * 版本号
     */
    SYS_VERSION("sysVersion","当前版本号"),
    /**
     * 服务器url
     */
    SYS_URL("sysUrl","服务器url"),
    /**
     *开发者
     */
    SYS_AUTHOR("sysAuthor","开发者"),
    /**
     * 开发者头像
     */
    SYS_AUTHOR_IMG("sysAuthorImg","开发者头像"),
    /**
     * 开发者邮箱
     */
    SYS_EMAIL("sysEmail","开发者邮箱"),
    SYS_COPY_RIGHT("sysCopyRight","版权所有"),
    SYS_UPDATE_TIME("sysUpdateTime","最后修改时间"),
    DEFAULT_CATEGORY("1","默认分类"),
    DEFAULT_TAG("1","默认标题");

    private final String configField;
    private final String configName;

    SysConfigConstants(String configField, String configName) {
        this.configField = configField;
        this.configName = configName;
    }

    public String getConfigField() {
        return configField;
    }

    public String getConfigName() {
        return configName;
    }
}

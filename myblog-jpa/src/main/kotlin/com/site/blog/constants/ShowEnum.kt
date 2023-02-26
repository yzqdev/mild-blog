package com.site.blog.constants

enum class ShowEnum(val status: Boolean, val note: String) {
    /**
     * 已删除
     */
    NOT_SHOW(false, "不显示"),

    /**
     * 未删除
     */
    SHOW(true, "显示");

}
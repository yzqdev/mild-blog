package com.site.blog.constants

enum class BlogStatusEnum(val status: Int, val note: String) {
    /**
     * 已发布
     */
    RELEASE(1, "已发布"),

    /**
     * 未发布
     */
    NO_RELEASE(0, "未发布");

}
package com.site.blog.constants

enum class LinkConstants(val linkTypeId: Int, val linkTypeName: String) {
    /**
     * 链接类型友谊
     */
    LINK_TYPE_FRIENDSHIP(0, "friend"),

    /**
     * 链接类型推荐
     */
    LINK_TYPE_RECOMMEND(1, "recommend"),

    /**
     * 链接类型私人
     */
    LINK_TYPE_PRIVATE(2, "private");

}
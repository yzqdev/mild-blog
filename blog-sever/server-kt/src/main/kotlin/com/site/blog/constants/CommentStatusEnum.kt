package com.site.blog.constants

enum class CommentStatusEnum(val status: Boolean, val note: String) {
    /**
     * 允许评论
     */
    ALLOW(true, "允许评论"),

    /**
     * 不允许评论
     */
    NO_ALLOW(false, "不允许评论");

}
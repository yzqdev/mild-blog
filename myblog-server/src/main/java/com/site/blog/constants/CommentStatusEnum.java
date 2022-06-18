package com.site.blog.constants;


public enum CommentStatusEnum {

    /**
     * 允许评论
     */
    ALLOW(true,"允许评论"),
    /**
     * 不允许评论
     */
    NO_ALLOW(false,"不允许评论");

    private final Boolean status;
    private final String note;

    CommentStatusEnum(Boolean status, String note) {
        this.status = status;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public Boolean getStatus() {
        return status;
    }
}

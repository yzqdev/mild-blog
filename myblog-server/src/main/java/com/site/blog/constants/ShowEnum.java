package com.site.blog.constants;


public enum ShowEnum {

    /**
     * 已删除
     */
    NOT_SHOW(false, "不显示"),
    /**
     * 未删除
     */
    SHOW(true, "显示");

    private final Boolean status;
    private final String note;

    ShowEnum(Boolean status, String note) {
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

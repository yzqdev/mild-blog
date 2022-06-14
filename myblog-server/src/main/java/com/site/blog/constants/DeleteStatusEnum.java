package com.site.blog.constants;

/**
 * @program: itoken
 * @classname: HttpStatusConstants
 * @description: Http状态常量
 * @author: 南街
 * @create: 2019-08-15 09:11
 **/
public enum DeleteStatusEnum {

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

    DeleteStatusEnum(Boolean status, String note) {
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

package com.site.blog.aop;

import lombok.Getter;

/**
 * 日志操作枚举
 *
 * @author yanni
 * @date 2022/06/16
 */
@Getter
public enum LogOperationEnum {

    /**
     * 其它
     */
    OTHER(1, "other"),

    /**
     * 增加
     */
    ADD(2, "add"),

    /**
     * 删除
     */
    DELETE(3, "delete"),

    /**
     * 编辑
     */
    EDIT(4, "edit"),

    /**
     * 更新
     */
    UPDATE(5, "update"),

    /**
     * 查询
     */
    QUERY(6, "query"),

    /**
     * 详情
     */
    DETAIL(7, "detail"),


    /**
     * 清空
     */
    CLEAN(8, "clean"),

    /**
     * 修改状态
     */
    CHANGE_STATUS(9, "change_status");
    final Integer idx;
    final String type;

    LogOperationEnum(int i, String type) {
        this.idx = i;
        this.type = type;
    }
}

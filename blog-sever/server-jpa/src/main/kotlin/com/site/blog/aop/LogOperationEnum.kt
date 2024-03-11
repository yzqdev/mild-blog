package com.site.blog.aop

/**
 * 日志操作枚举
 *
 * @author yanni
 * @date 2022/06/16
 */
enum class LogOperationEnum(val idx: Int, val type: String) {
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

}
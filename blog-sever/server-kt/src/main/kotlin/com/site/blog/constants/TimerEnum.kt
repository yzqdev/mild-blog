package com.site.blog.constants

import com.site.blog.exception.AbstractBaseException

enum class TimerEnum(override val code: Int, override val message: String) : AbstractBaseException {
    /**
     * 该条记录不存在
     */
    NOT_EXISTED(1, "您查询的该条记录不存在"),

    /**
     * 定时任务执行类不存在
     */
    TIMER_NOT_EXISTED(2, "定时任务执行类不存在"),

    /**
     * 检查定时任务启动时候的参数是否传了
     */
    EXE_EMPTY_PARAM(3, "请检查定时器的id，定时器cron表达式，定时任务是否为空！");

}
package com.site.blog.service

/**
 * @author yanni
 * @date time 2022/6/17 15:57
 * @modified By:
 */
interface TimerExeService {
    fun startTimer(taskId: String?, cron: String?, className: String?)
    fun stopTimer(taskId: String?)

}
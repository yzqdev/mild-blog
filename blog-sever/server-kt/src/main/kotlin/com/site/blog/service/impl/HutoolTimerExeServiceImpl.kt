package com.site.blog.service.impl

import cn.hutool.core.lang.Console
import cn.hutool.core.util.ObjectUtil
import cn.hutool.cron.CronUtil
import cn.hutool.cron.task.Task
import cn.hutool.extra.spring.SpringUtil
import com.site.blog.constants.TimerEnum
import com.site.blog.exception.ServiceException
import com.site.blog.service.TimerExeService
import com.site.blog.timer.TimerTaskRunner
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

@Service
@Slf4j
class HutoolTimerExeServiceImpl : TimerExeService {
    override fun startTimer(taskId: String?, cron: String?, className: String?) {
        if (ObjectUtil.hasEmpty(taskId, cron, className)) {
            Console.log("error ")
//            throw ServiceException(TimerEnum.EXE_EMPTY_PARAM.code)
        }

        // 预加载类看是否存在此定时任务类
        try {
            Class.forName(className)
        } catch (e: ClassNotFoundException) {
e.printStackTrace()
//            throw ServiceException(TimerEnum.TIMER_NOT_EXISTED.code)
        }

        // 定义hutool的任务
        val task = Task {
            try {
                val timerTaskRunner = SpringUtil.getBean(Class.forName(className)) as TimerTaskRunner
                timerTaskRunner.action()
            } catch (e: ClassNotFoundException) {
                Console.error(">>> 任务执行异常：{}", e.message)
            }
        }

        // 开始执行任务
        CronUtil.schedule(taskId, cron, task)
    }

    override fun stopTimer(taskId: String?) {
        CronUtil.remove(taskId)
    }
}
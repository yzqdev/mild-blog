package com.site.blog.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.extra.spring.SpringUtil;
import com.site.blog.constants.TimerEnum;
import com.site.blog.exception.ServiceException;
import com.site.blog.service.TimerExeService;
import com.site.blog.timer.TimerTaskRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HutoolTimerExeServiceImpl implements TimerExeService {


    @Override
    public void startTimer(String taskId, String cron, String className) {

        if (ObjectUtil.hasEmpty(taskId, cron, className)) {
            throw new ServiceException(TimerEnum.EXE_EMPTY_PARAM);
        }

        // 预加载类看是否存在此定时任务类
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ServiceException(TimerEnum.TIMER_NOT_EXISTED);
        }

        // 定义hutool的任务
        Task task = () -> {
            try {
                TimerTaskRunner timerTaskRunner = (TimerTaskRunner) SpringUtil.getBean(Class.forName(className));
                timerTaskRunner.action();
            } catch (ClassNotFoundException e) {
                log.error(">>> 任务执行异常：{}", e.getMessage());
            }
        };

        // 开始执行任务
        CronUtil.schedule(taskId, cron, task);
    }

    @Override
    public void stopTimer(String taskId) {
        CronUtil.remove(taskId);
    }

}
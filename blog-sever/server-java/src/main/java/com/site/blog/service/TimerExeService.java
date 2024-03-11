package com.site.blog.service;

/**
 * @author yanni
 * @date time 2022/6/17 15:57
 * @modified By:
 */
public interface TimerExeService {
    void startTimer(String taskId, String cron, String className);

    void stopTimer(String taskId);
}

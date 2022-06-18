package com.site.blog.timer.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleJobConfig {
    @Bean
    public JobDetail simpleJobDetail() {

        return JobBuilder.newJob(SimpleJob.class).withIdentity("myJob").storeDurably()
                .usingJobData("serviceCode", "显示swagger路径")
                .build();
    }

    @Bean
    public Trigger simpleJobTrigger() {
        //定义每1分钟执行一次
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(3).repeatForever();
        //定义触发器
        return TriggerBuilder.newTrigger().forJob(simpleJobDetail()).withIdentity("myJobTrigger").withSchedule(simpleScheduleBuilder).build();
    }
}
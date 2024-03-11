package com.site.blog.timer.quartz

import org.quartz.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SimpleJobConfig {
    @Bean
    fun simpleJobDetail(): JobDetail {
        return JobBuilder.newJob(SimpleJob::class.java).withIdentity("myJob").storeDurably()
            .usingJobData("serviceCode", "显示swagger路径")
            .build()
    }

    @Bean
    fun simpleJobTrigger(): Trigger {
        //定义每1分钟执行一次
        val simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(3).repeatForever()
        //定义触发器
        return TriggerBuilder.newTrigger().forJob(simpleJobDetail()).withIdentity("myJobTrigger")
            .withSchedule(simpleScheduleBuilder).build()
    }
}
package com.site.blog.timer.quartz

import com.site.blog.timer.quartz.service.OrderService
import com.site.blog.util.ColorUtil.Companion.green
import lombok.extern.slf4j.Slf4j
import org.quartz.JobExecutionContext
import org.springframework.scheduling.quartz.QuartzJobBean
import jakarta.annotation.Resource

@Slf4j
class SimpleJob : QuartzJobBean() {
    @Resource
    private val orderService: OrderService? = null
    var serviceCode: String? = null
    override fun executeInternal(context: JobExecutionContext) {
        green(serviceCode)
        orderService!!.showSwagger()
    }
}
package com.site.blog.timer.quartz;

import com.site.blog.timer.quartz.service.OrderService;
import com.site.blog.util.ColorUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;

import jakarta.annotation.Resource;


@Slf4j
public class SimpleJob extends QuartzJobBean {
    @Resource
    private OrderService orderService;
    private String serviceCode;

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) {

       ColorUtil.green(serviceCode);

        orderService.showSwagger();
    }

}
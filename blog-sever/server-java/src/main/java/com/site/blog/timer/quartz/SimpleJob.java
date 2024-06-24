package com.site.blog.timer.quartz;

import com.site.blog.timer.quartz.service.SwaggerService;
import com.site.blog.util.ColorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;


@Slf4j
@RequiredArgsConstructor

public class SimpleJob extends QuartzJobBean {

    private final SwaggerService swaggerService;


    @Override
    protected void executeInternal(JobExecutionContext context) {

        String serviceCode = "swagger";
        ColorUtil.green(serviceCode);

        swaggerService.showSwagger();
    }

}
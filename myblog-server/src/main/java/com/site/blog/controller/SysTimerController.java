package com.site.blog.controller;

import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.model.dto.AjaxPutPage;
import com.site.blog.model.dto.AjaxResultPage;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.SysTimer;
import com.site.blog.service.SysTimerService;
import com.site.blog.service.TimerExeService;
import com.site.blog.util.ResultGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author yanni
 * @date time 2022/6/17 16:08
 * @modified By:
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/admin/timer")
public class SysTimerController {
    private final SysTimerService sysTimerService;
private final TimerExeService timerExeService;
    @GetMapping("/list")
    public Result getTimers(AjaxPutPage<SysTimer> ajaxPutPage) {
        var page = ajaxPutPage.putPageToPage();

        sysTimerService.page(page);
        var res = new AjaxResultPage<SysTimer>();
        res.setCount(page.getTotal());
        res.setList(page.getRecords());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, res);


    }
    @PostMapping("/start/{id}")
    public  Result startTimer(@PathVariable("id") String id){
        var timer=sysTimerService.getById(id);
        timerExeService.startTimer(id,timer.getCron(),timer.getActionClass());
        return  ResultGenerator.getResultByHttp(HttpStatusEnum.OK,true,"启动成功");

    }

}

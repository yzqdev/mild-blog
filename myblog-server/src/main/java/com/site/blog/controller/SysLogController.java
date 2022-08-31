package com.site.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.model.dto.AjaxPutPage;
import com.site.blog.model.dto.AjaxResultPage;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.SysOpLog;
import com.site.blog.service.SysOpLogService;
import com.site.blog.util.ResultGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author yanni
 * @date time 2022/6/16 16:29
 * @modified By:
 */
@RestController
@RequestMapping("/v2/admin")
@RequiredArgsConstructor
public class SysLogController {
    private final SysOpLogService sysOpLogService;

    @PostMapping("/log")
    public Result<AjaxResultPage<SysOpLog>> getSyslogs(AjaxPutPage<SysOpLog> ajaxPutPage) {
        var page = ajaxPutPage.putPageToPage();
        sysOpLogService.page(page);
        var res = new AjaxResultPage<SysOpLog>();
        res.setCount(page.getTotal());
        res.setList(page.getRecords());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, res);

    }

    @DeleteMapping("/log/clear")
    public Result clearAll() {
        sysOpLogService.remove(new QueryWrapper<>());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, "");
    }
}

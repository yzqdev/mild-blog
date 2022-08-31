package com.site.blog.controller

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.site.blog.constants.HttpStatusEnum
import com.site.blog.model.dto.AjaxPutPage
import com.site.blog.model.dto.AjaxResultPage
import com.site.blog.model.dto.Result
import com.site.blog.model.entity.SysOpLog
import com.site.blog.service.SysOpLogService
import com.site.blog.util.ResultGenerator.getResultByHttp
import org.springframework.web.bind.annotation.*

/**
 * @author yanni
 * @date time 2022/6/16 16:29
 * @modified By:
 */
@RestController
@RequestMapping("/v2/admin")
class SysLogController(private val sysOpLogService: SysOpLogService) {
    @GetMapping("/log")
    fun getSyslogs(ajaxPutPage: AjaxPutPage<SysOpLog?>): Result<AjaxResultPage<SysOpLog?>> {
        val page = ajaxPutPage.putPageToPage()
        sysOpLogService.page(page)
        val res = AjaxResultPage<SysOpLog?>()
        res.count = page.total
        res.list = page.records
        return getResultByHttp(HttpStatusEnum.OK, true, res)
    }

    @DeleteMapping("/log/clear")
    fun clearAll(): Result<*> {
        sysOpLogService.remove(QueryWrapper())
        return getResultByHttp(HttpStatusEnum.OK, true, "")
    }
}
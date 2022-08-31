package com.site.blog.controller

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.site.blog.aop.LogOperationEnum
import com.site.blog.aop.SysLogAnnotation
import com.site.blog.constants.HttpStatusEnum
import com.site.blog.model.dto.*
import com.site.blog.model.entity.SysDictData
import com.site.blog.model.entity.SysDictType
import com.site.blog.service.SysDictDataService
import com.site.blog.service.SysDictTypeService
import com.site.blog.util.ResultGenerator.getResultByHttp
import org.springframework.beans.BeanUtils
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

/**
 * @author yanni
 * @date time 2022/6/17 13:03
 * @modified By:
 */
@RestController
@RequestMapping("/v2/admin")
class SysDictController(
    private val sysDictDataService: SysDictDataService,
    private val sysDictTypeService: SysDictTypeService
) {
    @GetMapping("/dict/list")
    fun dictList(ajaxPutPage: AjaxPutPage<SysDictType?>): Result<*> {
        val page = ajaxPutPage.putPageToPage()
        sysDictTypeService.page (page)
        val res = AjaxResultPage<SysDictType?>()
        res.list = page.records
        res.count = page.total
        return getResultByHttp(HttpStatusEnum.OK, true, res)
    }

    @PostMapping("/dict/add")
    @SysLogAnnotation(title = "添加字典类型", opType = LogOperationEnum.ADD)
    fun addDict(@RequestBody dictTypeDto: DictTypeDto?): Result<*> {
        val dicType = SysDictType()
        BeanUtils.copyProperties(dictTypeDto!!, dicType)
        dicType.status = true
        sysDictTypeService.save(dicType)
        return getResultByHttp(HttpStatusEnum.OK, true, dicType)
    }

    @DeleteMapping("/dict/clear/{dictType}")
    @Transactional(rollbackFor = [Exception::class])
    @SysLogAnnotation(title = "清除字典类型", opType = LogOperationEnum.CLEAN)
    fun clearDictType(@PathVariable("dictType") dictType: String?): Result<*> {
        sysDictDataService.remove(LambdaQueryWrapper<SysDictData>().eq(SysDictData::typeId, dictType))
        val res = sysDictTypeService.remove(LambdaQueryWrapper<SysDictType>().eq(SysDictType::id, dictType))
        return getResultByHttp(HttpStatusEnum.OK, true, res)
    }

    @PostMapping("/dictData/add")
    @SysLogAnnotation(title = "添加字典数据", opType = LogOperationEnum.ADD)
    fun addDict(@RequestBody dictDataDto: DictDataDto?): Result<*> {
        val dictData = SysDictData()
        BeanUtils.copyProperties(dictDataDto!!, dictData)
        dictData.status = true
        dictData.createTime = LocalDateTime.now()
        dictData.updateTime = LocalDateTime.now()
        sysDictDataService.save(dictData)
        return getResultByHttp(HttpStatusEnum.OK, true, dictData)
    }

    @GetMapping("/dictData/list/{dictType}")
    fun dictDataList(ajaxPutPage: AjaxPutPage<SysDictData?>, @PathVariable("dictType") dicType: String?): Result<*> {
        val page = ajaxPutPage.putPageToPage()
        val queryWrapper = KtQueryWrapper (SysDictData()).eq(SysDictData::typeId, dicType)
        sysDictDataService.page (page, queryWrapper)
        val res = AjaxResultPage<SysDictData?>()
        res.list = page.records
        res.count = page.total
        return getResultByHttp(HttpStatusEnum.OK, true, res)
    }
}
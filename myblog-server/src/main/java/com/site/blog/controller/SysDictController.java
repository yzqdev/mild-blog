package com.site.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.site.blog.aop.LogOperationEnum;
import com.site.blog.aop.SysLogAnnotation;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.model.dto.*;
import com.site.blog.model.entity.SysDictData;
import com.site.blog.model.entity.SysDictType;
import com.site.blog.service.SysDictDataService;
import com.site.blog.service.SysDictTypeService;
import com.site.blog.util.ResultGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author yanni
 * @date time 2022/6/17 13:03
 * @modified By:
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/admin")
public class SysDictController {
    private final SysDictDataService sysDictDataService;
    private final SysDictTypeService sysDictTypeService;

    @GetMapping("/dict/list")
    public Result dictList(AjaxPutPage<SysDictType> ajaxPutPage) {
        var page = ajaxPutPage.putPageToPage();
        sysDictTypeService.page(page);
        var res = new AjaxResultPage<SysDictType>();
        res.setList(page.getRecords());
        res.setCount(page.getTotal());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, res);
    }

    @PostMapping("/dict/add")
    @SysLogAnnotation(title = "添加字典类型",opType = LogOperationEnum.ADD)
    public Result addDict(@RequestBody DictTypeDto dictTypeDto) {
        var dicType = new SysDictType();
        BeanUtils.copyProperties(dictTypeDto, dicType);
        dicType.setStatus(true);
        sysDictTypeService.save(dicType);
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, dicType);
    }
    @DeleteMapping("/dict/clear/{dictType}")
    @Transactional(rollbackFor = Exception.class)
    @SysLogAnnotation(title = "清除字典类型",opType = LogOperationEnum.CLEAN)
    public Result clearDictType(@PathVariable("dictType") String dictType) {
         sysDictDataService.remove(new LambdaQueryWrapper<SysDictData>().eq(SysDictData::getTypeId,dictType));
        var res= sysDictTypeService.remove(new LambdaQueryWrapper<SysDictType>().eq(SysDictType::getId,dictType));
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, res);
    }
    @PostMapping("/dictData/add")
    @SysLogAnnotation(title = "添加字典数据",opType = LogOperationEnum.ADD)
    public Result addDict(@RequestBody DictDataDto dictDataDto) {
        var dictData = new SysDictData();
        BeanUtils.copyProperties(dictDataDto, dictData);

        dictData.setStatus(true);
        dictData.setCreateTime(LocalDateTime.now());
        dictData.setUpdateTime(LocalDateTime.now());
        sysDictDataService.save(dictData);
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, dictData);
    }

    @GetMapping("/dictData/list/{dictType}")
    public Result dictDataList(AjaxPutPage<SysDictData> ajaxPutPage,@PathVariable("dictType") String dicType) {
        var page = ajaxPutPage.putPageToPage();
        var queryWrapper=new LambdaQueryWrapper<SysDictData>().eq(SysDictData::getTypeId,dicType);
        sysDictDataService.page(page,queryWrapper);
        var res = new AjaxResultPage<SysDictData>();
        res.setList(page.getRecords());

        res.setCount(page.getTotal());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, res);
    }

}

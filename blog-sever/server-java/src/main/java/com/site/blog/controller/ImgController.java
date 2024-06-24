package com.site.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.site.blog.aop.LogOperationEnum;
import com.site.blog.aop.SysLogAnnotation;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.model.dto.AjaxPutPage;
import com.site.blog.model.dto.AjaxResultPage;
import com.site.blog.model.dto.PageDto;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.BlogInfo;
import com.site.blog.model.entity.Img;
import com.site.blog.model.entity.SysOpLog;
import com.site.blog.service.ImgService;
import com.site.blog.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author yanni
 * @date time 2021/10/29 12:49
 * @modified By:
 */
@RestController
@RequestMapping("/v2/admin/img")
@Slf4j
public class ImgController {
    private  final ImgService imgService;

    public ImgController(ImgService imgService) {
        this.imgService = imgService;
    }


    @PostMapping("/upload")
    @SysLogAnnotation(title = "上传图片",opType = LogOperationEnum.ADD)
    public Result uploadFileByEditor(@RequestParam(name = "img") MultipartFile file) {

        Map<String, Object> result = new HashMap<>();
        Img img=imgService.uploadImage(file);

        result.put("message", "上传成功");
        result.put("url", img.getImgUrl());
        result.put("img", img);

        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,true,result);
    }

    @PostMapping("/list")
    public Result<Object> listFiles(@RequestBody AjaxPutPage<Img> condition) {

        Page<Img> ipage = new Page<>(condition.getPage(), condition.getLimit());
        var imgs = imgService.page(ipage );


        var res = new AjaxResultPage<Img>();
        res.setCount(imgs.getTotal());
        res.setList(imgs.getRecords());

        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,true, res);
    }

    @DeleteMapping("/del/{id}")
    @SysLogAnnotation(title = "删除图片",opType = LogOperationEnum.DELETE)
    public Result delImg(@PathVariable String id) throws IOException { Img img = imgService.getById(id);
        Path file = Paths.get(img.getImgPath());
        Path thumbPath=Paths.get(img.getThumbnailPath());
        try {

            if (Files.exists(file)) {
                Files.deleteIfExists(file);
                log.info("删除文件：{}", file);
            }
            if (Files.exists(thumbPath)){
                Files.deleteIfExists(thumbPath);
                log.info("删除文件：{}", thumbPath);
            }

        } catch (Exception e) {

            throw new IOException(e.getMessage());
        }
        boolean flag = imgService.removeById(id);
        log.info(img.getImgPath());

        log.info("是否删除");
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, img);
        }

        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, null);
    }
}

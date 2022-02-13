package com.site.blog.controller;

import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.constants.property.UploadProperty;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.Img;
import com.site.blog.service.ImgService;
import com.site.blog.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
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
@RequestMapping("/v2/img")
@Slf4j
public class ImgController {
    @Resource
    private ImgService imgService;


    @PostMapping("/upload")
    public Map<String, Object> uploadFileByEditor(@RequestParam(name = "img") MultipartFile file) {

        Map<String, Object> result = new HashMap<>();
        Img img=imgService.uploadImage(file);
        result.put("success", 1);
        result.put("message", "上传成功");
        result.put("url", img.getImgUrl());
        result.put("img", img);

        return result;
    }

    @GetMapping("/list")
    public Result<Object> listFiles() {
        List<Img> imgs = imgService.list();


        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, imgs);
    }

    @DeleteMapping("/del/{id}")
    public Result delImg(@PathVariable String id) {
        try {
            Img img = imgService.getById(id);
            Path file = Paths.get(img.getImgPath());
            if (file.toFile().exists()) {
                Files.deleteIfExists(file);
                log.info("删除文件：{}", file);
            }
            boolean flag = imgService.removeById(id);
            log.info(img.getImgPath());

            log.info("是否删除");
            if (flag) {
                return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, img);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, null);
    }
}

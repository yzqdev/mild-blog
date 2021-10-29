package com.site.blog.controller;

import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.constants.UploadConstants;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.Img;
import com.site.blog.service.ImgService;
import com.site.blog.util.*;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
public class ImgController {
    @Resource
    private ImgService imgService;

    /**
     * 保存文章图片
     *
     * @param file
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @date 2019/8/26 13:57
     */

    @PostMapping("/upload")
    public Map<String, Object> uploadFileByEditormd(@RequestParam(name = "img") MultipartFile file) {
        String suffixName = UploadFileUtils.getSuffixName(file);
        //生成文件名称通用方法
        String newFileName = UploadFileUtils.getNewFileName(suffixName);
        File fileDirectory = new File(UploadConstants.FILE_UPLOAD_DIC);
        //创建文件
        File destFile = new File(UploadConstants.FILE_UPLOAD_DIC + newFileName);
        Map<String, Object> result = new HashMap<>(3);
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdirs()) {
                    throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
                }
            }

            file.transferTo(destFile);
            Img img = new Img();
            img.setImgName(destFile.getName());
            img.setImgPath(UploadConstants.FILE_UPLOAD_DIC + newFileName);
            img.setUploadTime(DateUtils.getLocalCurrentDate());
            img.setImgUrl("http://localhost:2801/" + newFileName);
            img.setImgSize(destFile.length());
            img.setImgType(destFile.getName().substring(destFile.getName().indexOf(".") + 1));
            img.setMd5(DigestUtils.md5DigestAsHex(new FileInputStream(destFile)));
            imgService.save(img);
            String fileUrl = UploadConstants.FILE_SQL_DIC + newFileName;
            result.put("success", 1);
            result.put("message", "上传成功");
            result.put("url", "http://localhost:2801/" + newFileName);
            result.put("img",img);
        } catch (IOException e) {

            e.printStackTrace();
        }
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
            File file = new File(img.getImgPath());
            if (file.exists()) {
                file.delete();
            }
            boolean flag = imgService.removeById(id);
            System.out.println(img.getImgPath());

            System.out.println("是否删除");
            if (flag) {
                return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, img);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, null);
    }
}

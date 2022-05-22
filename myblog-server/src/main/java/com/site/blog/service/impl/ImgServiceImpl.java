package com.site.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.constants.property.UploadProperty;
import com.site.blog.mapper.ImgMapper;
import com.site.blog.model.entity.Img;
import com.site.blog.service.ImgService;
import com.site.blog.util.DateUtils;
import com.site.blog.util.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanni
 * @date time 2021/10/29 12:47
 * @modified By:
 */
@Service
public class ImgServiceImpl extends ServiceImpl<ImgMapper, Img> implements ImgService {
    UploadProperty uploadProperty;
    private final Path staticDir;
    @Resource
    private ImgMapper imgMapper;
    @Value("${myblog.upload.pic-prefix}")
    private String picPrefix;
    @Value("${myblog.site.ip}")
    private String ip;

    public ImgServiceImpl(UploadProperty uploadProperty) {
        this.uploadProperty = uploadProperty;
        staticDir = Paths.get(uploadProperty.getPicUrl());
    }

    @Override
    public Img uploadImage(MultipartFile file) {
        String suffixName = UploadFileUtils.getSuffixName(file);
        //生成文件名称通用方法
        String newFileName = UploadFileUtils.getNewFileName(suffixName);

        //创建文件
        Path destFile = Paths.get(staticDir.toString(), newFileName);
        try {
            if (!Files.exists(staticDir)) {
                Files.createDirectory(staticDir);

            }
                Files.createFile(destFile);
                file.transferTo(destFile);


            Img img = new Img();
            img.setImgName(destFile.toFile().getName());

            img.setImgPath(destFile.toString());
            img.setUploadTime(DateUtils.getLocalCurrentDate());
            img.setImgUrl(ip + picPrefix + newFileName);
            img.setImgSize(Files.size(destFile));
            img.setImgType(getExtension(destFile));
            img.setMd5(DigestUtils.md5DigestAsHex(new FileInputStream(destFile.toFile())));
            imgMapper.insert(img);
            return img;
        } catch (IOException e) {

            e.printStackTrace();
        }
        return new Img();
    }
private String getExtension(Path path){

         return path.getFileName().toString().substring(path.getFileName().toString().indexOf(".")+1);
}
    @Override
    public void deleteImage(String fileName) {

    }

    @Override
    public void createDirectory(String path) {

    }
}

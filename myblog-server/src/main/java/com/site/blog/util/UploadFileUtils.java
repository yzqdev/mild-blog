package com.site.blog.util;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @program: my-blog
 * @classname: UploadFileUtils
 * @description: 上传文件工具类
 * @author: 朱林
 * @create: 2019-08-24 15:24
 **/
public class UploadFileUtils {

    /**
     *
     * 获取图片后缀
     *
     * @param file 文件
     * @return {@link String}
     * @since 2019/8/24 15:27
     */
    public static String getSuffixName(MultipartFile file){
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 获得新文件名
     * 生成文件名称通用方法
     *
     * @param suffixName 后缀名
     * @return {@link String}
     * @since  2019/8/24 15:29
     */
    public static String getNewFileName(String suffixName){
       DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        int random = new Random().nextInt(100);
        StringBuilder tempName = new StringBuilder();
        tempName.append(dateTimeFormatter.format(LocalDateTime.now())).append(random).append(suffixName);
        return tempName.toString();
    }
}

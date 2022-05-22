package com.site.blog.util;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
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
    public static String getExtension(@NonNull String filename) {
        Assert.hasText(filename, "Filename must not be blank");

        // Find the last slash
        int separatorLastIndex = StringUtils.lastIndexOf(filename, File.separatorChar);

        if (separatorLastIndex == filename.length() - 1) {
            return StringUtils.EMPTY;
        }

        if (separatorLastIndex >= 0 && separatorLastIndex < filename.length() - 1) {
            filename = filename.substring(separatorLastIndex + 1);
        }

        // Find last dot
        int dotLastIndex = StringUtils.lastIndexOf(filename, '.');

        if (dotLastIndex < 0) {
            return StringUtils.EMPTY;
        }

        String[] split = filename.split("\\.");

        List<String> extList = Arrays.asList("gz", "bz2");

        if (extList.contains(split[split.length - 1]) && split.length >= 3) {
            return filename.substring(filename.substring(0, dotLastIndex).lastIndexOf('.') + 1);
        }

        return filename.substring(dotLastIndex + 1);
    }

    /**
     * 获得新文件名
     * 生成文件名称通用方法
     *
     * @param suffixName 后缀名
     * @return {@link String}
     * @since  2019/8/24 15:29
     */
    public static String getNewFileName(String suffixName,Boolean thumbnail){
       DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

        StringBuilder tempName = new StringBuilder();
        if(thumbnail){
            tempName.append(dateTimeFormatter.format(LocalDateTime.now())).append("-thumb.").append(suffixName);
        }else{
            tempName.append(dateTimeFormatter.format(LocalDateTime.now())).append(".").append(suffixName);
        }

        return tempName.toString();
    }
    public static String getMD5(String path) {
        BigInteger bi = null;
        try {
            byte[] buffer = new byte[8192];
            int len = 0;
            MessageDigest md = MessageDigest.getInstance("MD5");
            File f = new File(path);
            FileInputStream fis = new FileInputStream(f);
            while ((len = fis.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fis.close();
            byte[] b = md.digest();
            bi = new BigInteger(1, b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bi.toString(16);
    }
}

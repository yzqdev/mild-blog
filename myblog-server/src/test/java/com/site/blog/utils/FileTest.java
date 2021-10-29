package com.site.blog.utils;

import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author yanni
 * @date time 2021/10/29 14:44
 * @modified By:
 */
public class FileTest {
    @Test
    public void removeFile(){
        File file=new File("D:/tmp/myblog/upload/20211029_14480916.png");
        boolean flag=file.delete();
        System.out.println(flag);
    }
}

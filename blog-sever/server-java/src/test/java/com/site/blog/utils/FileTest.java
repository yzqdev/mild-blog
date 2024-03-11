package com.site.blog.utils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

/**
 * @author yanni
 * @date time 2021/10/29 14:44
 * @modified By:
 */
class FileTest {
    @Test
    void removeFile(){
        File file=new File("C:\\Users\\yanni/.myblog/pic/20220213_14162033.jpg");
        boolean flag=file.delete();
        System.out.println(flag);
    }
    @Test
    void getFile(){
        Paths.get("C:\\Users\\yanni/.myblog/pic/20220213_14162033.jpg");
    }
}

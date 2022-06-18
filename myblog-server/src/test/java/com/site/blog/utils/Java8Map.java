package com.site.blog.utils;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import com.site.blog.util.MD5Utils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class Java8Map {
    @Test
    public void steam1 (){
        List<String> list = new ArrayList<>();
        list.add("java");
        list.add("php");
        list.add("python");

        //map function
        Stream<Integer> stream = list.stream().map(String::length);

        Integer[] lengthArr = stream.toArray(Integer[]::new);

        for (int a : lengthArr) {
            System.out.println(a);
        }
    }
    @Test
    void base64Test(){
        var str=HexUtil.encodeHexStr("student", CharsetUtil.CHARSET_UTF_8);
        Console.log(str);
        Console.log(HexUtil.decodeHex("73747564656e74"));
        Console.log(MD5Utils.MD5Encode("testPass","UTF-8"));
    }
}

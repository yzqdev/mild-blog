package com.site.blog.util;

import cn.hutool.core.lang.Console;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


public class DateUtils {
    public static void getDate() {
        List<String> myList =
                Arrays.asList("a1", "a2", "b1", "c2", "c1");

        myList
                .stream()
                .filter(s -> s.startsWith("c"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(Console::log);
    }

    /**
     * 获得本地当前时间
     *
     * @param
     * @return java.sql.Timestamp
     * @date 2019/8/28 13:03
     */
    public static Timestamp getLocalCurrentDate() {
        return new Timestamp(System.currentTimeMillis()) ;
    }
}

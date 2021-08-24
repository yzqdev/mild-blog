package com.site.blog.util;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

/**
 * @program: my-blog
 * @classname: DateUtils
 * @description:
 * @author: 朱林
 * @create: 2019-08-28 13:02
 **/
public class DateUtils {
    public static void getDate() {
        List<String> myList =
                Arrays.asList("a1", "a2", "b1", "c2", "c1");

        myList
                .stream()
                .filter(s -> s.startsWith("c"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);
    }

    /**
     * 获得本地当前时间
     *
     * @param
     * @return java.sql.Timestamp
     * @date 2019/8/28 13:03
     */
    public static Timestamp getLocalCurrentDate() {
        return new Timestamp(System.currentTimeMillis());
    }
}

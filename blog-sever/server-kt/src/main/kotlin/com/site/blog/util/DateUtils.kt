package com.site.blog.util

import cn.hutool.core.lang.Console
import java.sql.Timestamp
import java.util.*

object DateUtils {
    val date: Unit
        get() {
            val myList = Arrays.asList("a1", "a2", "b1", "c2", "c1")
            myList
                .stream()
                .filter { s: String -> s.startsWith("c") }
                .map { obj: String -> obj.uppercase(Locale.getDefault()) }
                .sorted()
                .forEach { obj: String? -> Console.log(obj) }
        }

    /**
     * 获得本地当前时间
     *
     * @param
     * @return java.sql.Timestamp
     * @date 2019/8/28 13:03
     */
    @JvmStatic
    val localCurrentDate: Timestamp
        get() = Timestamp(System.currentTimeMillis())
}
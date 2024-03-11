package com.site.blog

import com.site.blog.util.MD5Utils
import org.junit.jupiter.api.Test

/**
 * @author yzqde
 * @date time 2022/9/2 23:06
 * @modified By:
 *
 */
class Md5Test {
    @Test
    fun md5(){
        val pass=MD5Utils.MD5Encode("123456789","utf-8")
        println("14e1b600b1fd579f47433b88e8d85291")
        println(pass)
    }
}
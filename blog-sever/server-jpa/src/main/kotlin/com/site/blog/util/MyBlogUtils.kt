package com.site.blog.util

import org.springframework.util.StringUtils
import java.net.URI
import java.util.*

object MyBlogUtils {
    fun getHost(uri: URI): URI? {
        var effectiveURI: URI? = null
        effectiveURI = try {
            URI(uri.scheme, uri.userInfo, uri.host, uri.port, null, null, null)
        } catch (var4: Throwable) {
            null
        }
        return effectiveURI
    }

    fun cleanString(value: String): String {
        var value = value
        if (!StringUtils.hasText(value)) {
            return ""
        }
        value = value.lowercase(Locale.getDefault())
        value = value.replace("<".toRegex(), "& lt;").replace(">".toRegex(), "& gt;")
        value = value.replace("\\(".toRegex(), "& #40;").replace("\\)".toRegex(), "& #41;")
        value = value.replace("'".toRegex(), "& #39;")
        value = value.replace("onload".toRegex(), "0nl0ad")
        value = value.replace("xml".toRegex(), "xm1")
        value = value.replace("window".toRegex(), "wind0w")
        value = value.replace("click".toRegex(), "cl1ck")
        value = value.replace("var".toRegex(), "v0r")
        value = value.replace("let".toRegex(), "1et")
        value = value.replace("function".toRegex(), "functi0n")
        value = value.replace("return".toRegex(), "retu1n")
        value = value.replace("$".toRegex(), "")
        value = value.replace("document".toRegex(), "d0cument")
        value = value.replace("const".toRegex(), "c0nst")
        value = value.replace("eval\\((.*)\\)".toRegex(), "")
        value = value.replace("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']".toRegex(), "\"\"")
        value = value.replace("script".toRegex(), "scr1pt")
        value = value.replace("insert".toRegex(), "1nsert")
        value = value.replace("drop".toRegex(), "dr0p")
        value = value.replace("create".toRegex(), "cre0ate")
        value = value.replace("update".toRegex(), "upd0ate")
        value = value.replace("alter".toRegex(), "a1ter")
        value = value.replace("from".toRegex(), "fr0m")
        value = value.replace("where".toRegex(), "wh1re")
        value = value.replace("database".toRegex(), "data1base")
        value = value.replace("table".toRegex(), "tab1e")
        value = value.replace("tb".toRegex(), "tb0")
        return value
    }
}
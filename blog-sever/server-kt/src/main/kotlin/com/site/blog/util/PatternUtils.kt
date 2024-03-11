package com.site.blog.util

import java.util.regex.Pattern

object PatternUtils {
    /**
     * 匹配邮箱正则
     */
    private val VALID_EMAIL_ADDRESS_REGEX =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

    /**
     * 验证只包含中英文和数字的字符串
     *
     * @param keyword
     * @return
     */
    fun validKeyword(keyword: String?): Boolean {
        val regex = "^[a-zA-Z0-9\u4E00-\u9FA5]+$"
        val pattern = Pattern.compile(regex)
        val match = pattern.matcher(keyword)
        return match.matches()
    }

    /**
     * 判断是否是邮箱
     *
     * @param emailStr
     * @return
     */
    fun isEmail(emailStr: String?): Boolean {
        val matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr)
        return matcher.find()
    }

    /**
     * 判断是否是网址
     *
     * @param urlString
     * @return
     */
    fun isURL(urlString: String?): Boolean {
        val regex =
            "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$"
        val pattern = Pattern.compile(regex)
        return if (pattern.matcher(urlString).matches()) {
            true
        } else {
            false
        }
    }
}
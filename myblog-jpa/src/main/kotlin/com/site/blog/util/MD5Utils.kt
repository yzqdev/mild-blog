package com.site.blog.util

import java.security.MessageDigest

/**
 * md5加密
 */
object MD5Utils {
    private fun byteArrayToHexString(b: ByteArray): String {
        val resultSb = StringBuilder()
        for (value in b) {
            resultSb.append(byteToHexString(value))
        }
        return resultSb.toString()
    }

    private fun byteToHexString(b: Byte): String {
        var n = b.toInt()
        if (n < 0) n += 256
        val d1 = n / 16
        val d2 = n % 16
        return hexDigits[d1] + hexDigits[d2]
    }

    @JvmStatic
    fun MD5Encode(origin: String, charsetname: String): String {
        var resultString: String? = null
        try {
            resultString = origin
            val md = MessageDigest.getInstance("MD5")
            if (resultString != null) {
                resultString = if (charsetname == null || "" == charsetname) {
                    byteArrayToHexString(
                        md.digest(
                            resultString.toByteArray()
                        )
                    )
                } else {
                    byteArrayToHexString(
                        md.digest(
                            resultString
                                .toByteArray(charset(charsetname))
                        )
                    )
                }
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return resultString
    }

    private val hexDigits = arrayOf(
        "0", "1", "2", "3", "4", "5",
        "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"
    )
}
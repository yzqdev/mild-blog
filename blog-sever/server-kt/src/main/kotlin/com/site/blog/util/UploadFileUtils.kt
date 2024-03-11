package com.site.blog.util

import org.apache.commons.lang3.StringUtils
import org.springframework.util.Assert
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object UploadFileUtils {
    /**
     * 获取图片后缀
     *
     * @param file 文件
     * @return [String]
     * @since 2019/8/24 15:27
     */
    @JvmStatic
    fun getExtension(filename: String): String {
        var filename = filename
        Assert.hasText(filename, "Filename must not be blank")

        // Find the last slash
        val separatorLastIndex = StringUtils.lastIndexOf(filename, File.separatorChar.code)
        if (separatorLastIndex == filename.length - 1) {
            return StringUtils.EMPTY
        }
        if (separatorLastIndex >= 0 && separatorLastIndex < filename.length - 1) {
            filename = filename.substring(separatorLastIndex + 1)
        }

        // Find last dot
        val dotLastIndex = StringUtils.lastIndexOf(filename, '.'.code)
        if (dotLastIndex < 0) {
            return StringUtils.EMPTY
        }
        val split = filename.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val extList = Arrays.asList("gz", "bz2")
        return if (extList.contains(split[split.size - 1]) && split.size >= 3) {
            filename.substring(filename.substring(0, dotLastIndex).lastIndexOf('.') + 1)
        } else filename.substring(dotLastIndex + 1)
    }

    /**
     * 获得新文件名
     * 生成文件名称通用方法
     *
     * @param suffixName 后缀名
     * @return [String]
     * @since 2019/8/24 15:29
     */
    @JvmStatic
    fun getNewFileName(suffixName: String?, thumbnail: Boolean): String {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
        val tempName = StringBuilder()
        if (java.lang.Boolean.TRUE == thumbnail) {
            tempName.append(dateTimeFormatter.format(LocalDateTime.now())).append("-thumb.").append(suffixName)
        } else {
            tempName.append(dateTimeFormatter.format(LocalDateTime.now())).append(".").append(suffixName)
        }
        return tempName.toString()
    }

    @JvmStatic
    fun getMD5(path: String?): String {
        var bi: BigInteger? = null
        try {
            val buffer = ByteArray(8192)
            var len = 0
            val md = MessageDigest.getInstance("MD5")
            val f = File(path)
            val fis = FileInputStream(f)
            while (fis.read(buffer).also { len = it } != -1) {
                md.update(buffer, 0, len)
            }
            fis.close()
            val b = md.digest()
            bi = BigInteger(1, b)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bi!!.toString(16)
    }
}
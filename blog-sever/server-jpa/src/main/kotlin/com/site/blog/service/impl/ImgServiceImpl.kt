package com.site.blog.service.impl

import cn.hutool.core.lang.Console
import cn.hutool.log.LogFactory

import com.site.blog.constants.property.UploadProperty
import com.site.blog.mapper.ImgMapper
import com.site.blog.model.entity.Img
import com.site.blog.service.ImgService
import com.site.blog.util.UploadFileUtils.getExtension
import com.site.blog.util.UploadFileUtils.getMD5
import com.site.blog.util.UploadFileUtils.getNewFileName
import net.coobird.thumbnailator.Thumbnails
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDateTime
import java.util.*
import javax.imageio.ImageIO

/**
 * @author yanni
 * @date time 2021/10/29 12:47
 * @modified By:
 */
@Service

class ImgServiceImpl(private val uploadProperty: UploadProperty, private val imgMapper: ImgMapper) :  ImgService {
    var logger = LogFactory.get(ImgServiceImpl::class.java)
    private val staticDir: Path


    @Value("\${myblog.site.ip}")
    private val ip: String =""

    init {
        staticDir = Paths.get(uploadProperty.fileUrl)
    }

    override fun uploadImage(file: MultipartFile): Img {
        val suffixName = getExtension(file!!.originalFilename!!)
        //生成文件名称通用方法
        Console.log(suffixName)
        val newFileName = getNewFileName(suffixName, false)
        val thumbnailPath = getNewFileName(suffixName, true)
        //创建文件
        val destFile = Paths.get(staticDir.toString(), newFileName)
        val thumbPath = Path.of(staticDir.toString(), thumbnailPath)
        try {
            Files.createDirectories(staticDir)
            Files.createFile(destFile)
            file.transferTo(destFile)
            val img = Img()
            img.imgName = destFile.fileName.toString()
            img.imgPath = destFile.toString()
            img.uploadTime = LocalDateTime.now()
            img.imgUrl = uploadProperty.filePrefix + newFileName
            try {
                file.inputStream.use { `is` ->
                    // Generate thumbnail
                    val originalImage = ImageIO.read(`is`)
                    val result = generateThumbnail(
                        originalImage, thumbPath
                    )
                    if (result) {
                        // Set thumb path
                        img.thumbnailPath = thumbPath.toString()
                        //return thumbnailFilePath.getRelativePath();
                    }
                }
            } catch (e: Throwable) {
                logger.warn("Failed to open image file.", e)
            }
            img.imgSize = Files.size(destFile)

            img.mediaType = file.contentType?.let {
                MediaType.valueOf(

                    it

                ).toString()
            }
            img.imgType = getExtension(destFile)
            img.md5 = getMD5(destFile.toAbsolutePath().toString())
            imgMapper.save(img)
            return img
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Img()
    }

    private fun generateThumbnail(originalImage: BufferedImage, thumbPath: Path): Boolean {
        var result = false
        // Create the thumbnail
        try {
            Files.createFile(thumbPath)
            // Convert to thumbnail and copy the thumbnail
           Console.print("Trying to generate thumbnail: [{}]", thumbPath)
            Thumbnails.of(originalImage).size(THUMB_WIDTH, THUMB_HEIGHT).keepAspectRatio(true)
                .toFile(thumbPath.toFile())
            Console.log("Generated thumbnail image, and wrote the thumbnail to [{}]", thumbPath)
            result = true
        } catch (t: Throwable) {
            // Ignore the error
            logger.warn("Failed to generate thumbnail: $thumbPath", t)
        } finally {
            // Disposes of this graphics context and releases any system resources that it is using.
            originalImage.graphics.dispose()
        }
        return result
    }

    private fun getExtension(path: Path): String {
        return path.fileName.toString().substring(path.fileName.toString().indexOf(".") + 1)
    }

    override fun deleteImage(fileName: String) {}
    override fun createDirectory(path: String) {}
    override fun getById(id: Long): Img {
        return imgMapper.getReferenceById(id)
    }

    override fun removeById(id: Long) {
       imgMapper.deleteById(id)
    }

    companion object {
        /**
         * Thumbnail width.
         */
        private const val THUMB_WIDTH = 256

        /**
         * Thumbnail height.
         */
        private const val THUMB_HEIGHT = 256
    }
}
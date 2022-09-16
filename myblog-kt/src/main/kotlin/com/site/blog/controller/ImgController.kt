package com.site.blog.controller

import com.site.blog.aop.LogOperationEnum
import com.site.blog.aop.SysLogAnnotation
import com.site.blog.constants.HttpStatusEnum
import com.site.blog.model.entity.Img
import com.site.blog.service.ImgService
import com.site.blog.util.Result.getResultByHttp
import com.site.blog.util.ResultDto
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

/**
 * @author yanni
 * @date time 2021/10/29 12:49
 * @modified By:
 */
@RestController
@RequestMapping("/v2/img")
@Slf4j
class ImgController(private val imgService: ImgService) {
    val log =LoggerFactory.getLogger(this.javaClass)

    @PostMapping("/upload")
    @SysLogAnnotation(title = "上传图片", opType = LogOperationEnum.ADD)
    fun uploadFileByEditor(@RequestParam(name = "img") file: MultipartFile?): ResultDto<*> {
        val result: MutableMap<String, Any?> = HashMap()
        val img = imgService.uploadImage(file)
        result["message"] = "上传成功"
        result["url"] = img!!.imgUrl
        result["img"] = img
        return getResultByHttp<Map<String, Any?>>(HttpStatusEnum.OK, true, result)
    }

    @GetMapping("/list")
    fun listFiles(): ResultDto<Any> {
        val imgs = imgService.list()
        return getResultByHttp(HttpStatusEnum.OK, imgs)
    }

    @DeleteMapping("/del/{id}")
    @SysLogAnnotation(title = "删除图片", opType = LogOperationEnum.DELETE)
    @Throws(
        IOException::class
    )
    fun delImg(@PathVariable id: String?): ResultDto<*> {
        val img = imgService.getById(id)
        val file = Paths.get(img?.imgPath )
        val thumbPath = Paths.get(img?.thumbnailPath )
        try {
            if (Files.exists(file)) {
                Files.deleteIfExists(file)
                log.info("删除文件：{}", file)
            }
            if (Files.exists(thumbPath)) {
                Files.deleteIfExists(thumbPath)
                log.info("删除文件：{}", thumbPath)
            }
        } catch (e: Exception) {
            throw IOException(e.message)
        }
        val flag = imgService.removeById(id)

        return if (flag) {
            getResultByHttp<Img?>(HttpStatusEnum.OK, img)
        } else getResultByHttp<Any?>(HttpStatusEnum.OK, null)
    }
}
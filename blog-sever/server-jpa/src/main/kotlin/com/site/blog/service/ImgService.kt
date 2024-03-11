package com.site.blog.service


import com.site.blog.model.entity.Img
import org.springframework.web.multipart.MultipartFile

/**
 * @author yanni
 * @date time 2021/10/29 12:46
 * @modified By:
 */
interface ImgService  {
    fun uploadImage(file: MultipartFile): Img
    fun deleteImage(fileName: String)
    fun createDirectory(path: String)
   fun getById(id: Long): Img
    fun removeById(id: Long)
}
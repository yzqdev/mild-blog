package com.site.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.site.blog.model.entity.Img;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yanni
 * @date time 2021/10/29 12:46
 * @modified By:
 */
public interface ImgService extends IService<Img> {

     Img uploadImage(MultipartFile file);
     void deleteImage(String fileName);
     void createDirectory(String path);
}

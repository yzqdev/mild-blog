package com.site.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.constants.property.UploadProperty;
import com.site.blog.mapper.ImgMapper;
import com.site.blog.model.entity.Img;
import com.site.blog.service.ImgService;
import com.site.blog.util.DateUtils;
import com.site.blog.util.UploadFileUtils;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author yanni
 * @date time 2021/10/29 12:47
 * @modified By:
 */
@Service
@Slf4j
public class ImgServiceImpl extends ServiceImpl<ImgMapper, Img> implements ImgService {
    UploadProperty uploadProperty;
    private final Path staticDir;
    /**
     * Thumbnail width.
     */
    private static final int THUMB_WIDTH = 256;

    /**
     * Thumbnail height.
     */
    private static final int THUMB_HEIGHT = 256;
    @Resource
    private ImgMapper imgMapper;

    @Value("${myblog.site.ip}")
    private String ip;

    public ImgServiceImpl(UploadProperty uploadProperty) {
        this.uploadProperty = uploadProperty;
        staticDir = Paths.get(uploadProperty.getFileUrl());
    }

    @Override
    public Img uploadImage(MultipartFile file) {
        String suffixName = UploadFileUtils.getExtension(file.getOriginalFilename());
        //生成文件名称通用方法
        log.info(suffixName);
        String newFileName = UploadFileUtils.getNewFileName(suffixName,false);
String thumbnailPath=UploadFileUtils.getNewFileName(suffixName,true);
        //创建文件
        Path destFile = Paths.get(staticDir.toString(), newFileName);
        Path thumbPath = Path.of(staticDir.toString(), thumbnailPath);
        try {
            Files.createDirectories(staticDir);
                Files.createFile(destFile);
                file.transferTo(destFile);

            Img img = new Img();
            img.setImgName(destFile.getFileName().toString());

            img.setImgPath(destFile.toString());
            img.setUploadTime(DateUtils.getLocalCurrentDate());
            img.setImgUrl(uploadProperty.getFilePrefix()+ newFileName);
            try (InputStream is = file.getInputStream()) {
                // Generate thumbnail
                BufferedImage originalImage =
                        ImageIO.read(is);

                boolean result = generateThumbnail(originalImage, thumbPath
                       );
                if (result) {
                    // Set thumb path
                    img.setThumbnailPath(thumbPath.toString());
                    //return thumbnailFilePath.getRelativePath();
                }
            } catch (Throwable e) {
                log.warn("Failed to open image file.", e);
            }
            img.setImgSize(Files.size(destFile));
            log.info(MediaType.valueOf(Objects.requireNonNull(file.getContentType())).toString());
            img.setMediaType(MediaType.valueOf(Objects.requireNonNull(file.getContentType())).toString());
            img.setImgType(getExtension(destFile));
            img.setMd5(UploadFileUtils.getMD5(destFile.toAbsolutePath().toString()));
            imgMapper.insert(img);
            return img;
        } catch (IOException e) {

            e.printStackTrace();
        }
        return new Img();
    }
    private boolean generateThumbnail(BufferedImage originalImage, Path thumbPath ) {

        boolean result = false;
        // Create the thumbnail
        try {
            Files.createFile(thumbPath);
            // Convert to thumbnail and copy the thumbnail
            log.debug("Trying to generate thumbnail: [{}]", thumbPath);
            Thumbnails.of(originalImage).size(THUMB_WIDTH, THUMB_HEIGHT).keepAspectRatio(true)
                    .toFile(thumbPath.toFile());
            log.info("Generated thumbnail image, and wrote the thumbnail to [{}]", thumbPath);
            result = true;
        } catch (Throwable t) {
            // Ignore the error
            log.warn("Failed to generate thumbnail: " + thumbPath, t);
        } finally {
            // Disposes of this graphics context and releases any system resources that it is using.
            originalImage.getGraphics().dispose();
        }
        return result;
    }
private String getExtension(Path path){

         return path.getFileName().toString().substring(path.getFileName().toString().indexOf(".")+1);
}
    @Override
    public void deleteImage(String fileName) {

    }

    @Override
    public void createDirectory(String path) {

    }
}

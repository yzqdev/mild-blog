package com.site.blog.controller.common;

import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.model.dto.Result;
import com.site.blog.util.ResultGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author yanni
 * @date time 2022/1/20 1:43
 * @modified By:
 */
@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class CommonController {
    @Value("${myblog.upload.file-url}")
    private  String fileUrl;

    @PostMapping("/file")
    public Result uploadFile(@RequestBody MultipartFile multipartFile) {
        try {
            multipartFile.transferTo(new File(fileUrl + multipartFile.getOriginalFilename()));
            HashMap<String, Object> result = new HashMap<>();
            result.put("hhh", "hhh");
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,result);
        }catch (IOException e){
            e.printStackTrace();
        }

        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }
}

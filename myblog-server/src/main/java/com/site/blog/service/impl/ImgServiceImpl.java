package com.site.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.mapper.ImgMapper;
import com.site.blog.model.entity.Img;
import com.site.blog.service.ImgService;
import org.springframework.stereotype.Service;

/**
 * @author yanni
 * @date time 2021/10/29 12:47
 * @modified By:
 */
@Service
public class ImgServiceImpl extends ServiceImpl<ImgMapper, Img> implements ImgService {
}

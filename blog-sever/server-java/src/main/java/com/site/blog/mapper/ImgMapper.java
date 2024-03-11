package com.site.blog.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.site.blog.model.entity.Img;

/**
 * @author yanni
 * @date time 2021/10/29 12:46
 * @modified By:
 */
public interface ImgMapper extends BaseMapper<Img> {


  Integer selectCount();
  List<Img> imgs();
}

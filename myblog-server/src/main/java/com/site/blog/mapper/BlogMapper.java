package com.site.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.site.blog.model.entity.BlogInfo;
import com.site.blog.util.PageQueryUtils;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogMapper extends BaseMapper<BlogInfo>{

}
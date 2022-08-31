package com.site.blog.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.site.blog.mapper.SysDictTypeMapper
import com.site.blog.model.entity.SysDictType
import com.site.blog.service.SysDictTypeService
import org.springframework.stereotype.Service

/**
 * @author yanni
 * @date time 2022/6/17 12:54
 * @modified By:
 */
@Service
class SysDictTypeServiceImpl : ServiceImpl<SysDictTypeMapper?, SysDictType?>(), SysDictTypeService
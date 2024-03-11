package com.site.blog.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.site.blog.mapper.LinkMapper
import com.site.blog.model.entity.Link
import com.site.blog.service.LinkService
import org.springframework.stereotype.Service

/**
 *
 *
 * 友情链接表 服务实现类
 *
 *
 */
@Service
class LinkServiceImpl : ServiceImpl<LinkMapper?, Link?>(), LinkService
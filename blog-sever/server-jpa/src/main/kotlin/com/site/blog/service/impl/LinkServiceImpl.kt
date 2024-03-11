package com.site.blog.service.impl


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
class LinkServiceImpl(private val linkMapper: LinkMapper) :  LinkService {
    override fun removeById(linkId: Long) {
        linkMapper.deleteById(linkId)
    }

    override fun save(link: Link) {
        linkMapper.save(link)
    }
}
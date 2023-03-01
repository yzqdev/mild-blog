package com.site.blog.service


import com.site.blog.model.entity.Link

/**
 *
 *
 * 友情链接表 服务类
 *
 *
 */
interface LinkService {
      fun removeById(linkId: Long)
      fun save(link: Link)
}
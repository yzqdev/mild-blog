package com.site.blog.service.impl


import com.site.blog.mapper.BlogConfigMapper
import com.site.blog.model.entity.BlogConfig
import com.site.blog.service.BlogConfigService
import org.springframework.stereotype.Service
import java.util.stream.Collectors

/**
 *
 *
 * 服务实现类
 *
 *
 */
@Service
class BlogConfigServiceImpl(private val blogConfigMapper: BlogConfigMapper) :
    BlogConfigService {
    override fun allConfigs(): Map<String, String> {
        val list  = blogConfigMapper.findAll( )
        println(list)
//        return list.associateBy({ it.code  }, { it.value })
        return mapOf()

    }
}
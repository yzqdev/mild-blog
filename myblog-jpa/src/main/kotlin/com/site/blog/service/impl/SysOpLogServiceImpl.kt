package com.site.blog.service.impl


import com.site.blog.mapper.SysOpLogMapper
import com.site.blog.model.entity.SysOpLog
import com.site.blog.service.SysOpLogService
import org.springframework.stereotype.Service

/**
 * @author yanni
 * @date time 2022/6/16 2:10
 * @modified By:
 */
@Service
class SysOpLogServiceImpl(private val sysOpLogMapper: SysOpLogMapper) : SysOpLogService {
    override fun removeAll() {
       sysOpLogMapper.deleteAll()
    }
}
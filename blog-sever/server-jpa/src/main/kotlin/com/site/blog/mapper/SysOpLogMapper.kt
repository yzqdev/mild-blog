package com.site.blog.mapper


import com.site.blog.model.entity.SysOpLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


/**
 * @author yanni
 * @date time 2022/6/16 2:08
 * @modified By:
 */
@Repository
interface SysOpLogMapper : JpaRepository<SysOpLog,Long>
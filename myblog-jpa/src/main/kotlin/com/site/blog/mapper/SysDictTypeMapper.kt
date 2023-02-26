package com.site.blog.mapper


import com.site.blog.model.entity.SysDictType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


/**
 * @author yanni
 * @date time 2022/6/17 12:52
 * @modified By:
 */
@Repository
interface SysDictTypeMapper : JpaRepository<SysDictType,Long>
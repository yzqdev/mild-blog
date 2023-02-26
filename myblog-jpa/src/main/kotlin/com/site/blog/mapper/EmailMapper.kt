package com.site.blog.mapper


import com.site.blog.model.entity.EmailConfig
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


/**
 * @author yanni
 * @date time 2022/6/18 1:25
 * @modified By:
 */
@Repository
interface EmailMapper : JpaRepository<EmailConfig,Long>
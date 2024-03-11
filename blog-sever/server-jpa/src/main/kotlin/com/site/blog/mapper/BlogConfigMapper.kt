package com.site.blog.mapper


import com.site.blog.model.entity.BlogConfig
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface BlogConfigMapper : JpaRepository<BlogConfig,Long>
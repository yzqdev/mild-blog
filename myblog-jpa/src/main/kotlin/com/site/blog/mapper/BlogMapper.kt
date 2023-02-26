package com.site.blog.mapper


import com.site.blog.model.entity.BlogInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface BlogMapper : JpaRepository<BlogInfo,Long>
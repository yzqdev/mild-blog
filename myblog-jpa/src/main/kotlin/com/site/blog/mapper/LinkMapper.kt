package com.site.blog.mapper


import com.site.blog.model.entity.Link
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


/**
 *
 *
 * 友情链接表 Mapper 接口
 *
 *
 * @author zhulin
 * @since 2019-09-02
 */
@Repository
interface LinkMapper : JpaRepository<Link,Long>
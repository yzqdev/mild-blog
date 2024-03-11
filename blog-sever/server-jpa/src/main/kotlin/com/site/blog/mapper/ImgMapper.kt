package com.site.blog.mapper


import com.site.blog.model.entity.Img
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


/**
 * @author yanni
 * @date time 2021/10/29 12:46
 * @modified By:
 */
@Repository
interface ImgMapper : JpaRepository<Img,Long>
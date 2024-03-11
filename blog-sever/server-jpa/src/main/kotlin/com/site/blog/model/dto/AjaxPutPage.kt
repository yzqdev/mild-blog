package com.site.blog.model.dto

import com.baomidou.mybatisplus.extension.plugins.pagination.Page

data class AjaxPutPage<T> (
    /**
     * 当前页码
     */
    var page: Int? = null,

    /**
     * 每页显示
     */
    var limit: Int? = null,

    /**
     * 从多少开始
     */
    var start: Int? = null,
    var show: Boolean? = null,

    /**
     * 删除
     */
    var deleted: Boolean? = null,

    /**
     * 条件类
     */
    var condition: T? = null,



){
    /**
     * 将符合Layui的格式转成mybtais-plus分页的page
     *
     * @return
     */
    fun putPageToPage(): Page<T> {
        return Page(page!!.toLong(), limit!!.toLong())
    }
}
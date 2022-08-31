package com.site.blog.util

/**
 * 分页查询参数
 *
 * @author nanjie
 */
class PageQueryUtils(params: Map<String?, Any>) : LinkedHashMap<String?, Any?>() {
    //当前页码
    var page: Int

    //每页条数
    var limit: Int

    init {
        this.putAll(params)

        //分页参数
        page = params["page"].toString().toInt()
        limit = params["limit"].toString().toInt()
        this["start"] = (page - 1) * limit
        this["page"] = page
        this["limit"] = limit
    }

    override fun toString(): String {
        return "PageUtil{" +
                "page=" + page +
                ", limit=" + limit +
                '}'
    }
}
package com.site.blog.util

import lombok.Data
import java.io.Serializable

@Data
class PageResult(//列表数据
    private val list: List<*>, //总记录数
    private val totalCount: Long, //每页记录数
    private val pageSize: Int, //当前页数
    private val currPage: Int
) : Serializable {
    //总页数
    private val totalPage: Int

    /**
     * 分页
     *
     * @param list       列表数据
     * @param totalCount 总记录数
     * @param pageSize   每页记录数
     * @param currPage   当前页数
     */
    init {
        totalPage = Math.ceil(totalCount.toDouble() / pageSize).toInt()
    }
}
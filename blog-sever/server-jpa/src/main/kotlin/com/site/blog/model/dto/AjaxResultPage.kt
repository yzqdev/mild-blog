package com.site.blog.model.dto

import java.io.Serializable

/**分页表格数据返回
 * @author yanni
 */
data class AjaxResultPage<T>  (
    /**
     * 状态码
     */
    var  code:Int? = 0,

    /**
     * 提示消息
     */
    var  msg: String? = null,

    /**
     * 总条数
     */
    var  count: Long = 0,

    /**
     * 表格数据
     */
    var  list: List<T>? = null
)
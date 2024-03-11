package com.site.blog.context

import cn.hutool.core.lang.Dict
import cn.hutool.core.util.ObjectUtil

/**
 * @author yanni
 * @date time 2022/6/17 21:06
 * @modified By:
 */
object ConfigContext {
    /**
     * 所有的常量，可以增删改查
     */
    private val CONSTANTS_HOLDER = Dict.create()

    /**
     * 添加系统常量
     *
     */
    fun putConstant(code: String?, value: Any?) {
        if (ObjectUtil.hasEmpty(code, value)) {
            return
        }
        CONSTANTS_HOLDER.put(code, value)
    }

    /**
     * 删除常量，系统常量无法删除，在sysConfig已判断
     *
     */
    fun deleteConstant(code: String?) {
        if (ObjectUtil.hasEmpty(code)) {
            return
        }
        CONSTANTS_HOLDER.remove(code)
    }

    /**
     * 获取系统常量本身
     */
    @JvmStatic
    fun me(): Dict {
        return CONSTANTS_HOLDER
    }
}
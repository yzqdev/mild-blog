package com.site.blog.aop

import java.lang.annotation.Inherited

/**
 * 系统日志
 *
 * @author yanni
 * @date 2022/06/16
 */
@Inherited
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class SysLogAnnotation(
    /**
     * 业务的名称,例如:"修改菜单"
     */
    val title: String = "",
    /**
     * 业务操作类型枚举
     */
    val opType: LogOperationEnum = LogOperationEnum.OTHER
)
package com.site.blog.aop;

import java.lang.annotation.*;

/**
 * 系统日志
 *
 * @author yanni
 * @date 2022/06/16
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SysLogAnnotation {

    /**
     * 业务的名称,例如:"修改菜单"
     */
    String title() default "";

    /**
     * 业务操作类型枚举
     */
    LogOperationEnum opType() default LogOperationEnum.OTHER;
}

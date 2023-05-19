package com.site.blog.aop

import cn.hutool.core.lang.Console
import cn.hutool.core.util.ObjectUtil
import com.site.blog.context.UserContextHolder
import com.site.blog.model.entity.BlogConfig
import com.site.blog.model.entity.SysOpLog
import com.site.blog.service.BlogConfigService
import com.site.blog.service.SysOpLogService
import com.site.blog.util.JoinPointUtil.getArgsJsonString
import com.site.blog.util.RequestHelper

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Aspect
@Configuration
class SysLogAspect(var sysOpLogService: SysOpLogService,val blogConfigService: BlogConfigService) {




    /**
     * 日志切入点
     *
     * @author xuyuxiang
     * @date 2020/3/23 17:10
     */
    @Pointcut("@annotation(com.site.blog.aop.SysLogAnnotation)")
    private fun getLogPointCut() {

    }


    /**
     * 操作成功返回结果记录日志
     */
    @AfterReturning(pointcut = "getLogPointCut()", returning = "result")
    fun doAfterReturning(joinPoint: JoinPoint, result: Any?) {
        val methodSignature = joinPoint.signature as MethodSignature
        val method = methodSignature.method
        val businessLog = method.getAnnotation(SysLogAnnotation::class.java)
        val sysOpLog = genBaseSysOpLog()
        val className = joinPoint.target.javaClass.name
        val methodName = joinPoint.signature.name
        val param = getArgsJsonString(joinPoint)
        assert(sysOpLog != null)
        sysOpLog!!.name = businessLog.title
        sysOpLog.opType = businessLog.opType.type
        sysOpLog.className = className
        sysOpLog.methodName = methodName
        sysOpLog.param = param
        sysOpLog.opTime = LocalDateTime.now()
        sysOpLog.account = UserContextHolder.me().user()?.uuid
        val sqlBlogConfig = blogConfigService.getOne(
            QueryWrapper<BlogConfig> ().eq(

               "config_code", "sysUpdateTime"
            )
        )
        println("获取sqlblogconfig ,${sqlBlogConfig.toString()}")
        sqlBlogConfig?.configValue = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        sysOpLogService.save(sysOpLog)
        blogConfigService.updateById(sqlBlogConfig)
        Console.log("更新系统修改日期")
        //异步记录日志
    }

    /**
     * 操作发生异常记录日志
     */
    @AfterThrowing(pointcut = "getLogPointCut()", throwing = "exception")
    fun doAfterThrowing(joinPoint: JoinPoint, exception: Exception?) {
        val methodSignature = joinPoint.signature as MethodSignature
        val method = methodSignature.method
        val businessLog = method.getAnnotation(SysLogAnnotation::class.java)
    }

    private fun genBaseSysOpLog(): SysOpLog? {
        val request = RequestHelper.getRequest()
        if (ObjectUtil.isNotNull(request)) {
            val ip = RequestHelper.getRequestIp()
            val address = "aa" //IpAddressUtil.getAddress(request);
            val browser = RequestHelper.getBrowserFull()
            val os =RequestHelper.getUa().os.name
            val url = request.requestURI
            val method = request.method
            return genBaseLog(ip, address, browser, os, url, method)
        }
        return null
    }

    fun genBaseLog(
        ip: String?,
        location: String?,
        browser: String?,
        os: String?,
        url: String?,
        method: String?
    ): SysOpLog {
        val sysOpLog = SysOpLog()
        sysOpLog.ip = ip
        sysOpLog.location = location
        sysOpLog.browser = browser
        sysOpLog.os = os
        sysOpLog.url = url
        sysOpLog.reqMethod = method
        return sysOpLog
    }
}
package com.site.blog.aop

import com.site.blog.service.BlogConfigService
import jakarta.annotation.Resource
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*


/**
 * @author yanni
 */
@Aspect
@Order(5)
@Configuration
class WebLogAspect {
    private val startTime = ThreadLocal<Long>()

    @Resource
    var blogConfigService: BlogConfigService? = null

    //使用@Pointcut定义一个切入点，可以是一个规则表达式，比如下例中某个package下的所有函数，也可以是一个注解等。
    @Pointcut("execution(public * com.site.blog.controller.*.*(..))")
    fun webLog() {
    }

    private val log = LoggerFactory.getLogger(this.javaClass)

    /**
     * 使用@Before在切入点开始处切入内容
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    @Throws(Throwable::class)
    fun doBefore(joinPoint: JoinPoint) {
        startTime.set(System.currentTimeMillis())

        // 接收到请求，记录请求内容
        val attributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?
        val request = attributes!!.request

        // 记录下请求内容
        log.info("请求URL : " + request.requestURL.toString())
        log.info("请求HTTP_METHOD : " + request.method)
        log.info("请求IP : " + request.remoteAddr)
        log.info("请求CLASS_METHOD : " + joinPoint.signature.declaringTypeName + "." + joinPoint.signature.name)
        log.info("请求参数值 : " + Arrays.toString(joinPoint.args))
    }

    //使用@AfterReturning在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    @Throws(Throwable::class)
    fun doAfterReturning(ret: Any) {
        // 处理完请求，返回内容
        log.info("响应RESPONSE : $ret")
        log.info("响应时间SPEND TIME : " + (System.currentTimeMillis() - startTime.get()) + "ms")
    }
}
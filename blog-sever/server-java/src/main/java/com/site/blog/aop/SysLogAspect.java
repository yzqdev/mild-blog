package com.site.blog.aop;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.site.blog.context.UserContextHolder;
import com.site.blog.model.entity.BlogConfig;
import com.site.blog.model.entity.SysOpLog;
import com.site.blog.service.BlogConfigService;
import com.site.blog.service.SysOpLogService;
import com.site.blog.util.JoinPointUtil;
import com.site.blog.util.RequestHelper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Configuration
public class SysLogAspect {
    @Resource
    BlogConfigService blogConfigService;
    @Resource
    SysOpLogService sysOpLogService;

    /**
     * 日志切入点
     *
     * @author xuyuxiang
     * @date 2020/3/23 17:10
     */
    @Pointcut("@annotation(com.site.blog.aop.SysLogAnnotation)")
    private void getLogPointCut() {

    }

    /**
     * 操作成功返回结果记录日志
     */
    @AfterReturning(pointcut = "getLogPointCut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        var businessLog = method.getAnnotation(SysLogAnnotation.class);
        SysOpLog sysOpLog = genBaseSysOpLog();
        String className = joinPoint.getTarget().getClass().getName();

        String methodName = joinPoint.getSignature().getName();

        String param = JoinPointUtil.getArgsJsonString(joinPoint);

        assert sysOpLog != null;
        sysOpLog.setName(businessLog.title());
        sysOpLog.setOpType(businessLog.opType().getType());

        sysOpLog.setClassName(className);
        sysOpLog.setMethodName(methodName);
        sysOpLog.setParam(param);
        sysOpLog.setOpTime(LocalDateTime.now());
        sysOpLog.setAccount(UserContextHolder.me().getUser().getUuid());

        BlogConfig sqlBlogConfig = blogConfigService.getOne(new LambdaQueryWrapper<BlogConfig>().eq(BlogConfig::getConfigCode, "sysUpdateTime"));

        sqlBlogConfig.setConfigValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        sysOpLogService.save(sysOpLog);
        blogConfigService.updateById(sqlBlogConfig);
        Console.log("更新系统修改日期");
        //异步记录日志

    }

    /**
     * 操作发生异常记录日志
     */
    @AfterThrowing(pointcut = "getLogPointCut()", throwing = "exception")
    public void doAfterThrowing(JoinPoint joinPoint, Exception exception) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        var businessLog = method.getAnnotation(SysLogAnnotation.class);

    }

    private SysOpLog genBaseSysOpLog() {
        HttpServletRequest request = RequestHelper.getRequest();
        if (ObjectUtil.isNotNull(request)) {
            String ip = RequestHelper.getRequestIp();
            String address = "aa"; //IpAddressUtil.getAddress(request);
            String browser = RequestHelper.getBrowserFull();
            String os = RequestHelper.getUa().getOs().getName();
            String url = request.getRequestURI();
            String method = request.getMethod();
            return genBaseLog(ip, address, browser, os, url, method);
        }
        return null;
    }

    SysOpLog genBaseLog(String ip, String location, String browser, String os, String url, String method) {
        SysOpLog sysOpLog = new SysOpLog();
        sysOpLog.setIp(ip);
        sysOpLog.setLocation(location);
        sysOpLog.setBrowser(browser);
        sysOpLog.setOs(os);
        sysOpLog.setUrl(url);
        sysOpLog.setReqMethod(method);
        return sysOpLog;
    }
}
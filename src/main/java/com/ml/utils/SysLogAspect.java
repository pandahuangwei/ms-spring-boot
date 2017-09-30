package com.ml.utils;

import com.alibaba.fastjson.JSON;
import com.ml.annotation.SysLog;
import com.ml.entity.sys.Log;
import com.ml.service.sys.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author panda.
 * @since 2017-07-30 19:04.
 */
@Aspect
@Component
public class SysLogAspect {
    @Autowired
    private LogService logService;

    @Pointcut("@annotation(com.ml.annotation.SysLog)")
    public void logPointCut() {

    }
    @Before("logPointCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Log sysLog = new Log();
        SysLog syslog = method.getAnnotation(SysLog.class);
        if(syslog != null){
            //注解上的描述
            sysLog.setOperation(syslog.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args[0]);
        sysLog.setParams(params);

        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        sysLog.setIp(IPs.getIpAddr(request));

        //用户名
        String username = Shiros.getUser().getUsername();
        sysLog.setUsername(username);

        sysLog.setCreateDt(new Date());
        //保存系统日志
        logService.save(sysLog);
    }
}

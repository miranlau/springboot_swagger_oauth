package com.tieto.springbootdemo.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class TraceLoggingAspect {
    private static Logger logger = LoggerFactory.getLogger(TraceLoggingAspect.class);

    private ThreadLocal<Long> methodStartTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.tieto.springbootdemo.service..*.*(..))")
    public void serviceTraceLoggingPointCut(){}

    @Before("serviceTraceLoggingPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String method = signature.getMethod().getName();
        Object[] args = joinPoint.getArgs();

        StringBuilder stringBuilder = new StringBuilder("Enter ");
        stringBuilder.append(className).append(".").append(method).append(" with args: ")
                .append(Arrays.asList(args));

        logger.trace(stringBuilder.toString());
        methodStartTime.set(System.currentTimeMillis());
    }

    @AfterReturning(pointcut = "serviceTraceLoggingPointCut()", returning = "ret")
    public void doAfter(JoinPoint joinPoint, Object ret) {
        Long methodEndTime = System.currentTimeMillis();
        long elapsed = methodEndTime - methodStartTime.get();

        String className = joinPoint.getTarget().getClass().getSimpleName();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String method = signature.getMethod().getName();
        Object[] args = joinPoint.getArgs();

        StringBuilder stringBuilder = new StringBuilder("Leave ");
        stringBuilder.append(className).append(".").append(method).append(" with return: ")
                .append(ret).append(", invocation elapsed(ms): ").append(elapsed);

        logger.trace(stringBuilder.toString());
    }

    @AfterThrowing(value = "serviceTraceLoggingPointCut()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e) {

    }

}


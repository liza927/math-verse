package com.mathverse.core.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.mathverse.core.controller..*(..))")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("Вызов метода: {}",joinPoint.getSignature().getName());

        Object[] arg = joinPoint.getArgs();
        log.info("Аргументы вызова: {}",java.util.Arrays.toString(arg));

        Object result = joinPoint.proceed();
        log.info("Результат метода: {}",result);
        return result;
    }
}

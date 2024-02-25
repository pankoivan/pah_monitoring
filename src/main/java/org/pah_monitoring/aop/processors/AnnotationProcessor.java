package org.pah_monitoring.aop.processors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class AnnotationProcessor {

    @Around("@annotation(org.pah_monitoring.aop.annotations.NullWhenNull)")
    public Object nullWhenNull(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("AAAA");
        return null;
        //return joinPoint.proceed();
    }

}

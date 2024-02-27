package org.pah_monitoring.main.aop.processors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class AnnotationProcessor {

    @Around("@annotation(org.pah_monitoring.main.aop.annotations.NullWhenNull)")
    public Object nullWhenNull(ProceedingJoinPoint joinPoint) throws Throwable {
        if (joinPoint.getArgs().length == 0 || joinPoint.getArgs().length > 1) {
            return joinPoint.proceed();
        } else {
            if (joinPoint.getArgs()[0] == null) {
                return null;
            } else {
                return joinPoint.proceed();
            }
        }
    }

}

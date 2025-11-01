package org.example.smartspring.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class PerformanceMonitoringAspect {

    private static final long SLOW_METHOD_THRESHOLD = 1000;


    @Pointcut("within(org.example.smartspring.service..*)")
    public void serviceLayer() {}


    @Around("serviceLayer()")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;

            if (executionTime > SLOW_METHOD_THRESHOLD) {
                log.warn("⚡ MÉTHODE LENTE DÉTECTÉE: {} a pris {}ms (seuil: {}ms)",
                        methodName, executionTime, SLOW_METHOD_THRESHOLD);
            } else {
                log.trace(" Performance: {} exécuté en {}ms", methodName, executionTime);
            }

            return result;
        } catch (Throwable e) {
            long executionTime = System.currentTimeMillis() - startTime;
            log.error("⚡ ERREUR après {}ms dans {}", executionTime, methodName);
            throw e;
        }
    }
}

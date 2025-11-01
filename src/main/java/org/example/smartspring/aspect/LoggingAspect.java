package org.example.smartspring.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("within(org.example.smartspring.services..*)")
    public void serviceLayer() {}

    @Pointcut("within(org.example.smartspring.controller..*)")
    public void controllerLayer() {}

    @Before("serviceLayer() || controllerLayer()")
    public void logBefore(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.debug("→ {}.{}({})",
                className.substring(className.lastIndexOf('.') + 1),
                methodName,
                Arrays.toString(args));
    }

    @AfterReturning(pointcut = "serviceLayer() || controllerLayer()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        log.debug("← {}.{} returned: {}",
                className.substring(className.lastIndexOf('.') + 1),
                methodName,
                result != null ? result.getClass().getSimpleName() : "null");
    }

    @Around("serviceLayer()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;

        if (executionTime > 3000) {
            log.warn("⚡ SLOW METHOD: {}.{} took {}ms - Consider optimization",
                    joinPoint.getSignature().getDeclaringTypeName().substring(
                            joinPoint.getSignature().getDeclaringTypeName().lastIndexOf('.') + 1),
                    joinPoint.getSignature().getName(),
                    executionTime);
        }

        return result;
    }
}

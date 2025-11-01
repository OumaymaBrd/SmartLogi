package org.example.smartspring.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
@Slf4j
public class ExceptionLoggingAspect {

    @Pointcut("within(org.example.smartspring..*)")
    public void applicationPackage() {}

    @AfterThrowing(pointcut = "applicationPackage()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.error("╔════════════════════════════════════════════════════════════════");
        log.error("║ EXCEPTION CAPTURÉE");
        log.error("╠════════════════════════════════════════════════════════════════");
        log.error("║ Classe      : {}", className);
        log.error("║ Méthode     : {}", methodName);
        log.error("║ Arguments   : {}", Arrays.toString(args));
        log.error("║ Type        : {}", exception.getClass().getSimpleName());
        log.error("║ Message     : {}", exception.getMessage());
        log.error("╚════════════════════════════════════════════════════════════════");


        if (log.isDebugEnabled()) {
            log.debug("Stack trace complète:", exception);
        }
    }

    @Pointcut("within(org.example.smartspring.exception..*)")
    public void customExceptions() {}


    @AfterThrowing(pointcut = "customExceptions()", throwing = "exception")
    public void logBusinessException(JoinPoint joinPoint, Throwable exception) {
        log.warn(" Exception métier: {} dans {}.{}",
                exception.getMessage(),
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }
}

package org.example.smartspring.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TransactionLoggingAspect {

    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void transactionalMethods() {}

    @Before("transactionalMethods()")
    public void logTransactionStart(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.debug("Transaction started for: {}", methodName);
    }

    @AfterReturning("transactionalMethods()")
    public void logTransactionComplete(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.debug("Transaction completed for: {}", methodName);
    }
}

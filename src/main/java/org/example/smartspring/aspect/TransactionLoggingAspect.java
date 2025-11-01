package org.example.smartspring.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class TransactionLoggingAspect {


    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void transactionalMethod() {}


    @Before("transactionalMethod()")
    public void logTransactionStart(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        log.debug(" Début de transaction pour: {}", methodName);
    }


    @AfterReturning("transactionalMethod()")
    public void logTransactionSuccess(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        log.debug(" Transaction réussie pour: {}", methodName);
    }


    @AfterThrowing(pointcut = "transactionalMethod()", throwing = "exception")
    public void logTransactionFailure(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().toShortString();
        log.error(" Transaction échouée pour: {} - Raison: {}", methodName, exception.getMessage());
    }
}

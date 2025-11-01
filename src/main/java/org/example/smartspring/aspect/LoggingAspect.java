package org.example.smartspring.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.smartspring.annotation.Loggable;
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


    @Pointcut("within(org.example.smartspring.repository..*)")
    public void repositoryLayer() {}


    @Pointcut("@annotation(org.example.smartspring.annotation.Loggable)")
    public void loggableMethod() {}


    @Around("serviceLayer() || controllerLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.debug("→ Entrée dans {}.{}() avec arguments: {}",
                className, methodName, Arrays.toString(args));

        Object result;
        try {
            result = joinPoint.proceed();

            long executionTime = System.currentTimeMillis() - startTime;

            log.debug("← Sortie de {}.{}() avec résultat: {} | Temps d'exécution: {}ms",
                    className, methodName, result, executionTime);

            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            log.error("✗ Erreur dans {}.{}() après {}ms: {}",
                    className, methodName, executionTime, e.getMessage());
            throw e;
        }
    }


    @Around("loggableMethod() && @annotation(loggable)")
    public Object logLoggableMethod(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable {
        long startTime = System.currentTimeMillis();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();

        if (loggable.logParams()) {
            log.info("→ [{}] Appel de {}.{}() avec paramètres: {}",
                    loggable.level(), className, methodName, Arrays.toString(joinPoint.getArgs()));
        } else {
            log.info("→ [{}] Appel de {}.{}()", loggable.level(), className, methodName);
        }

        Object result;
        try {
            result = joinPoint.proceed();

            long executionTime = System.currentTimeMillis() - startTime;

            if (loggable.logResult()) {
                log.info("← [{}] Fin de {}.{}() - Résultat: {} | Temps: {}ms",
                        loggable.level(), className, methodName, result, executionTime);
            } else if (loggable.logExecutionTime()) {
                log.info("← [{}] Fin de {}.{}() | Temps: {}ms",
                        loggable.level(), className, methodName, executionTime);
            }

            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            log.error("✗ [{}] Erreur dans {}.{}() après {}ms",
                    loggable.level(), className, methodName, executionTime, e);
            throw e;
        }
    }


    @Before("repositoryLayer()")
    public void logRepositoryCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        log.debug("🗄️ Appel repository: {}.{}()", className, methodName);
    }


    @AfterReturning(pointcut = "repositoryLayer()", returning = "result")
    public void logRepositoryReturn(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.debug("🗄️ Repository {}.{}() retourne: {}",
                joinPoint.getSignature().getDeclaringTypeName(), methodName, result);
    }
}

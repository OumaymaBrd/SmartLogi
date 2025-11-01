package org.example.smartspring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {

    String level() default "INFO";

    boolean logParams() default true;

    boolean logResult() default true;

    boolean logExecutionTime() default true;
}

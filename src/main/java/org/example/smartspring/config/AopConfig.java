package org.example.smartspring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@Configuration
@EnableAspectJAutoProxy
public class AopConfig {
    // Configuration AOP activée via @EnableAspectJAutoProxy
    // Les aspects seront automatiquement détectés et appliqués

}

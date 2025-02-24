package com.crio.RentRead.Utilty;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect 
{
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    @Before("execution(* com.crio.RentRead.Controller.*.*(..))") // Pointcut: Before any method in any class inside the Controller package
    public void logApiAccess() // Advice
    {
        System.out.println("Welcome to RentRead application");
        logger.info("Welcome to RentRead application");
    }
}

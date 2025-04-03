package com.echevarne.sap.cloud.facturacion.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class MethodLoggingAspect {
	
	private static final Logger LOG = LoggerFactory.getLogger(MethodLoggingAspect.class);	

    @Pointcut("@annotation(com.echevarne.sap.cloud.facturacion.util.Loggable)")
    public void loggableMethods() {
    }

    @Before("loggableMethods()")
    public void logMethodEntry(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        LOG.info("INICIO de : {}", methodName);
    }

    @AfterReturning(pointcut = "loggableMethods()", returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();
        LOG.info("SALIDA de: {}", methodName);
    }

    @AfterThrowing(pointcut = "loggableMethods()", throwing = "exception")
    public void logMethodException(JoinPoint joinPoint, Exception exception) {
        String methodName = joinPoint.getSignature().toShortString();
        String exceptionName = exception.getClass().getSimpleName();
        LOG.error("EXCEPCION [{}] en {}: {}", exceptionName, methodName, exception.getMessage());
    }
    
}

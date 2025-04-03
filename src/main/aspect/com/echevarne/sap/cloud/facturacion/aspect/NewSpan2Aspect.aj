package com.echevarne.sap.cloud.facturacion.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cloud.sleuth.Span;

import com.echevarne.sap.cloud.facturacion.util.NewSpan2;
import com.echevarne.sap.cloud.facturacion.util.TracerHolder;

@Aspect
public class NewSpan2Aspect {

    @Around("@annotation(newSpan2) && execution(* *(..))")
    public Object traceMethod(ProceedingJoinPoint joinPoint, NewSpan2 newSpan2) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String spanName = newSpan2.value().isEmpty() ? signature.getMethod().getName() : newSpan2.value();
        Span newSpan = TracerHolder.getTracer().nextSpan().name(spanName).start();
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            newSpan.error(throwable);
            throw throwable;
        } finally {
            newSpan.end();
        }
    }
}

package com.echevarne.sap.cloud.facturacion.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AnnotationUtils {

    public static Map<String, Object> extractMemberValues(Annotation annotation){
        Class<? extends Annotation> annotationClass = annotation.annotationType();
        final Method[] methods = annotationClass.getDeclaredMethods();

        Map<String, Object> memberValues = new HashMap<>(methods.length);
        for (Method method : methods) {
            final String methodName = method.getName();
            try {
                memberValues.put(methodName, method.invoke(annotation));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException("Can't execute annotation method", e);
            }

        }

        return memberValues;
    }

    public static Map<String, Object> extractDefaultMemberValues(Annotation annotation){
        Class<? extends Annotation> annotationClass = annotation.annotationType();
        return extractDefaultMemberValues(annotationClass);
    }

    public static Map<String, Object> extractDefaultMemberValues(Class<? extends Annotation> annotationClass){
        final Method[] methods = annotationClass.getDeclaredMethods();

        Map<String, Object> memberValues = new HashMap<>(methods.length);
        for (Method method : methods) {
            final String methodName = method.getName();
            memberValues.put(methodName, method.getDefaultValue());
        }

        return memberValues;
    }
}

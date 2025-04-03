package com.echevarne.sap.cloud.facturacion.reflection;

import com.echevarne.sap.cloud.facturacion.reflection.impl.ObjectReflectionUtilImpl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;

public interface ObjectReflectionUtil {

    // Use injected service if possible
    ObjectReflectionUtil OBJECT_REFLECTION_UTIL = new ObjectReflectionUtilImpl();

    Object get(Object object, String path) throws Exception;

    void setValue(final String path, Object object, Object objValue) throws Exception;

    <T> T setFieldValueFromObject(final Object obj, final String fieldName, Function<Field, T> prepareValueFunction) throws IllegalAccessException, InvocationTargetException, NoSuchFieldException;

    Object getFieldParentObject(Object obj, String attributePath);

    Object getFieldValueFromObject(final Object obj, final String fieldName) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException;

    Collection<?> createAndSetTargetCollection(Object object, String path) throws Exception;

    void copyFields(Object source, Object target, Set<String> blackListFieldNames);

    Set<Field> getAllFields(Class<?> clazz);

    boolean hasGetter(Class<?> clazz, String name);
}

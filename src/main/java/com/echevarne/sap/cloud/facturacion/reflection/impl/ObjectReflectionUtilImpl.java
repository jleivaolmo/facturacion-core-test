package com.echevarne.sap.cloud.facturacion.reflection.impl;

import com.echevarne.sap.cloud.facturacion.reflection.ObjectReflectionUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ObjectReflectionUtilImpl implements ObjectReflectionUtil {
    /**
     * Obtiene el valor de un path.
     *
     * @param path the path
     * @return the object
     * @throws Exception the exception
     */
    public Object get(Object object, String path) throws Exception {
        Object obj = getFieldParentObject(object, path);
        if (obj == null) {
            throw new Exception("Object not found for path " + path);
        }

        final String fieldName = getFieldNameFrom(path);
        return getFieldValueFromObject(obj, fieldName);
    }

    /**
     * Asigna un valor a un path.
     *
     * @param path the path
     * @param objValue the obj value
     * @throws Exception the exception
     */
    public void setValue(final String path, Object object, Object objValue) throws Exception {
        if(objValue != null) { // We should handle better null values. Moved here for keeping compatibility
            final Object targetObj = getFieldParentObject(object, path);
            final String fieldName = getFieldNameFrom(path);

            
        }
    }

    public <T> T setFieldValueFromObject(final Object obj, final String fieldName, Function<Field, T> prepareValueFunction) throws IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        boolean valueSet = false;
        T value = null;

        final Class<?> objClass = obj.getClass();
        final List<Class<?>> allObjClasses = getAllClasses(objClass);

        final Field field = getField(allObjClasses, fieldName);
        final Class<?> fieldTypeClass = field.getType();

        // Try using the setters

        final List<String> setterNames = calculateSetterNames(fieldName);

        for(String setterName: setterNames){
            Method method = null;

            for(Class<?> currentObjClass: allObjClasses){
                try{
                    method = currentObjClass.getMethod(setterName, fieldTypeClass);
                    break;
                } catch (NoSuchMethodException e){
                    // Nothing to do
                    if(log.isDebugEnabled()){
                        log.debug("Method {} not found in the class {}", fieldName, currentObjClass.getSimpleName());
                    }
                }
            }

            if (method != null) {
                valueSet = true;

                value = prepareValueFunction.apply(field);
                method.invoke(obj, value);
            }

            if (valueSet){
                break;
            }
        }

        if (!valueSet){
            if(!field.isAccessible()){
                field.setAccessible(true);
            }

            value = prepareValueFunction.apply(field);
            field.set(obj, value);

            if(isEntity(obj)){
                log.warn(
                        "Setter method {} not found in the class {}. Falling back to property manipulation. Can cause problems with change detection.",
                        fieldName,
                        obj.getClass().getSimpleName()
                );
            }


        }

        return value;
    }

    private boolean isEntity(Object obj) {
        return obj != null && obj.getClass().getAnnotation(Entity.class) != null;
    }

    /**
     * Obtiene el objeto padre de una atributo.
     *
     * @param obj the obj
     * @param attributePath the attribute path
     * @return the field parent object
     */
    public Object getFieldParentObject(Object obj, String attributePath) {
        Object parentObject = null;

        try {
            int idx = attributePath.indexOf(".");
            if (idx > -1) {
                final String fieldName = attributePath.substring(0, idx);

                final Object superObject = getFieldValueFromObject(obj, fieldName);

                final String attributePath2 = attributePath.substring(idx + 1);
                parentObject = getFieldParentObject(superObject, attributePath2);

            } else {
                parentObject = obj;
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            log.info("La ruta del objecto {} path {} no ha podido determinarse (getFieldParentObject)", obj, attributePath, e);
        }

        return parentObject;
    }

    public Object getFieldValueFromObject(final Object obj, final String fieldName) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        Object value;

        final Class<?> objClass = obj.getClass();
        final Method method = getGetterMethod(fieldName, objClass);

        if (method != null) {
            value = method.invoke(obj);
        } else {
            // Fallback to field access
            if(isEntity(obj)) {
                log.warn(
                        "Getter method {} not found in the class {}. Falling back to property access. Can cause problems with lazy loading.",
                        fieldName,
                        objClass.getSimpleName()
                );
            }

            final List<Class<?>> allObjClasses = getAllClasses(objClass);
            final Field field = getField(allObjClasses, fieldName);

            if(!field.isAccessible()){
                field.setAccessible(true);
            }

            value = field.get(obj);
        }

        return value;
    }

    private Method getGetterMethod(final String fieldName, Class<?> objClass) throws NoSuchFieldException {
        Method method = null;

        final List<Class<?>> allObjClasses = getAllClasses(objClass);
        final Field field = getField(allObjClasses, fieldName);
        final Class<?> fieldClass = field.getType();

        final List<String> getterNames = calculateGetterNames(fieldName, fieldClass);

        for(String getterName: getterNames){

            for(Class<?> currentObjClass: allObjClasses){
                try{
                    method = currentObjClass.getMethod(getterName);
                    break;
                } catch (NoSuchMethodException e){
                    // Nothing to do
                    if(log.isDebugEnabled()){
                        log.debug("Method {} not found in the class {}", fieldName, currentObjClass.getSimpleName());
                    }
                }
            }

            if (method != null){
                break;
            }
        }

        return method;
    }

    private Field getField(final List<Class<?>> allClasses, String fieldName) throws NoSuchFieldException {
        Field field = null;

        for(Class<?> clazz: allClasses) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                // Nothing to do
                if(log.isDebugEnabled()){
                    log.debug("Field {} not found in the class {}", fieldName, clazz.getSimpleName());
                }
            }
        }

        if (field == null) {
            throw new NoSuchFieldException("Field not found [FieldName: " + fieldName + "]");
        }

        return field;
    }

    private List<Class<?>> getAllClasses(Class<?> objectClass) {
        final List<Class<?>> superclasses = getSuperClasses(objectClass);

        final List<Class<?>> allClasses = new ArrayList<>(superclasses.size() + 1);
        allClasses.add(objectClass);
        allClasses.addAll(superclasses);

        return allClasses;
    }

    private List<Class<?>> getSuperClasses(Class<?> objectClass) {
        final List<Class<?>> classList = new LinkedList<>();

        Class<?> superclass = objectClass.getSuperclass();

        while (superclass != null) {
            classList.add(superclass);
            superclass = superclass.getSuperclass();
        }

        return classList;
    }

    private List<String> calculateSetterNames(final String fieldName) {
        final String capitalizedFieldName = StringUtils.capitalize(fieldName);

        return Collections.singletonList("set" + capitalizedFieldName);
    }

    private List<String> calculateGetterNames(final String fieldName, final Class<?> fieldType) {
        final String capitalizedFieldName = StringUtils.capitalize(fieldName);

        final boolean isBoolean = fieldType.equals(boolean.class) || fieldType.equals(Boolean.class);

        final List<String> getterNames = new ArrayList<>(isBoolean ? 2 : 1);

        if (isBoolean) {
            getterNames.add("is" + capitalizedFieldName);
        }
        getterNames.add("get" + capitalizedFieldName);

        return getterNames;
    }

    public Collection<?> createAndSetTargetCollection(Object object, String path) throws Exception {
        final Object targetObj = getFieldParentObject(object, path);

        String attributeName = getFieldNameFrom(path);
        return setFieldValueFromObject(targetObj, attributeName, (field -> {
            final Class<?> targetType = field.getType();
            return createCollection(targetType);
        }));
    }

    @Override
    public void copyFields(Object source, Object target, Set<String> blackListFieldNames) {
        final Class<?> sourceClass = source.getClass();
        final Class<?> targetClass = target.getClass();

        if (!sourceClass.equals(targetClass)) {
            throw new RuntimeException(
                    "Source class and target class must be the same [SourceClass: "
                            + sourceClass.getSimpleName()
                            + "TargetClass: "
                            + targetClass.getSimpleName()
                            + "]"
            );
        }

        final Set<String> allFieldNames = getAllFieldNames(sourceClass);

        final Set<String> fieldsToSet = new HashSet<>(allFieldNames);
        fieldsToSet.removeAll(blackListFieldNames);

        for(String fieldToSet: fieldsToSet) {
            try {
                final Object fieldValue = getFieldValueFromObject(source, fieldToSet);
               
            } catch (NoSuchFieldException | InvocationTargetException | IllegalAccessException e) {
                throw new IllegalStateException(
                        "Could not get/set field [FieldName: "
                                + fieldToSet + ", Class: "
                                + sourceClass.getSimpleName()
                                + "]", e);
            }
        }
    }

    @Override
    public Set<Field> getAllFields(Class<?> clazz) {
        final List<Class<?>> allClasses = getAllClasses(clazz);

        final Set<Field> fields = new HashSet<>();
        for(Class<?> currClass: allClasses){
            final Field[] classFields = currClass.getDeclaredFields();
            for(Field classField: classFields){
                if (!Modifier.isStatic(classField.getModifiers())) {
                    if (!classField.getName().startsWith("$")) {
                        fields.add(classField);
                    }
                }
            }
        }
        return fields;
    }

    @Override
    public boolean hasGetter(Class<?> clazz, String name) {
        boolean hasGetter = false;

        try{
            final Method method = getGetterMethod(name, clazz);

            hasGetter = method != null;
        } catch (NoSuchFieldException e) {
            // Field doesn't exist
        }

        return hasGetter;
    }

    private Set<String> getAllFieldNames(Class<?> clazz) {
        return this.getAllFields(clazz).stream()
                .map(Field::getName)
                .collect(Collectors.toSet());
    }

    private Collection<?> createCollection(final Class<?> targetType) {
        if (targetType.equals(List.class) || targetType.equals(Collection.class)) {
            return new ArrayList<>();
        } else if (targetType.equals(Set.class)) {
            return new HashSet<>();
        } else {
            try {
                return (Collection<?>) targetType.newInstance();	// is this possible ??? -> Yes, but only if it's a class
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalArgumentException("Can't handle this type of collection [TypeClass: " + targetType.getSimpleName() +"]");
            }
        }
    }

    /**
     * Obtiene el nombre del campo de un path.
     *
     * @param path the path
     * @return the field name from
     */
    private String getFieldNameFrom(String path) {
        String fieldName = path;

        int idx = path.lastIndexOf(".");
        if (idx > -1) {
            fieldName = path.substring(idx + 1);
        }

        return fieldName;
    }
}

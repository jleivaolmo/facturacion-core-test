package com.echevarne.sap.cloud.facturacion.model.priority;

import java.lang.reflect.Field;
import java.util.function.BiFunction;

import com.echevarne.sap.cloud.facturacion.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

import static com.echevarne.sap.cloud.facturacion.reflection.ObjectReflectionUtil.OBJECT_REFLECTION_UTIL;

@Data
@AllArgsConstructor
public class SelectOptionItem <T> {

    private T valorCampo;
    private BiFunction<T, T, Boolean> operacion;

    private static BiFunction equals = Object::equals;

     public SelectOptionItem(T valorCampo) {
        this(valorCampo, equals);   // por default se usa equals como funcion de comparacion
    }

    /**
     * Aplica el atributo operacion al contenido del field del object y al atributo valorCampo
     * @param field
     * @param object
     * @return el resultado de aplicar el atributo operacion al contenido del field del object y al atributo valorCampo
     */
    public boolean match(Field field, Object object) {
        boolean matches = false;
        try {
            final T fieldValue = (T) OBJECT_REFLECTION_UTIL.getFieldValueFromObject(object, field.getName());
            matches = StringUtils.equalsAnyOrValue(fieldValue, valorCampo);
        } catch (Exception ex) {
            // Nothing to do
        }
        return matches;
    }

    public boolean matchEmpty(Field field,Object object) {
        boolean matches = false;
        try {
            final T fieldValue = (T) OBJECT_REFLECTION_UTIL.getFieldValueFromObject(object, field.getName());
            if (fieldValue != null) {
                matches = operacion.apply(fieldValue, valorCampo);
            } else {
                matches = true;
            }
        } catch (Exception ex) {
            // Nothing to do
        }
        return matches;
    }

    public boolean matchEmptyAndAny(Field field,Object object) {
        boolean matches = false;
        try {
            final T fieldValue = (T) OBJECT_REFLECTION_UTIL.getFieldValueFromObject(object, field.getName());

            if(fieldValue == null) {
                matches = true;
            } else if(field.getType() == int.class && (Integer) fieldValue == 0) {
                matches = true;
            }  else if (fieldValue.equals(StringUtils.ANY)){
                matches = true;
            } else {
                matches = operacion.apply(fieldValue, valorCampo);
            }
        } catch (Exception ex) {
            // Nothing to do
        }

        return matches;
    }
}

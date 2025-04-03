package com.echevarne.sap.cloud.facturacion.gestioncambios;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.echevarne.sap.cloud.facturacion.reflection.ObjectReflectionUtil.OBJECT_REFLECTION_UTIL;

@Slf4j
public class EntityMapUtil {
    public static List<String> getIds(Class<?> clazz){
        List<String> ids = AnnotationUtil.getUniqueIndexFields(clazz);

        if (ids.size() == 0) {
            throw new RuntimeException("Can get ids without unique index on class " + clazz.getCanonicalName());
        }

        return ids;
    }

    public static <T> Map<List<Object>, T> getMap(List<String> ids, Set<T> tSet) {
        Map<List<Object>, T> map = new HashMap<>(tSet.size());

        for (T t : tSet) {
            List<Object> key = getKey(ids, t);
            map.put(key, t);
        }

        return map;
    }

    public static List<Object> getKey(List<String> ids, final Object object) {
        return ids.stream().map(fieldName -> {
            try {
                Object key = OBJECT_REFLECTION_UTIL.getFieldValueFromObject(object, fieldName);
                if (key instanceof BasicEntity) {
                    // Use entity key instead
                    final List<String> valueIds = getIds(key.getClass());
                    key = getKey(valueIds, key);
                }

                return key;
            } catch (NoSuchFieldException | InvocationTargetException | IllegalAccessException ex) {
                throw new IllegalArgumentException("Error getting key field value [FieldName: + " + fieldName + ", Class: " + object.getClass().getSimpleName() + "]", ex);
            }
        }).collect(Collectors.toList());
    }
}

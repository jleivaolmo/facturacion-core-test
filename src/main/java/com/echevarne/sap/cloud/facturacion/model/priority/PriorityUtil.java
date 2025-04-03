package com.echevarne.sap.cloud.facturacion.model.priority;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.util.StringUtils;

import lombok.var;

import static com.echevarne.sap.cloud.facturacion.reflection.ObjectReflectionUtil.OBJECT_REFLECTION_UTIL;

public class PriorityUtil {

    public final static int ANY_PRIORITY = 1;
    public final static int NO_PRIORITY = 0;
    public final static int NEGATIVE_PRIORITY = -100;

    public static Stream<Field> priorizableFieldStream(Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> field.getAnnotation(Priorizable.class) != null);
    }

    /**
     * Se recorre la entityList y para cada entidad se obtiene la prioridad usando el selectOption y se crea un
     * SelectOptionResult. La lista se ordena por prioridad.
     * @param entityList es la lista de entidades
     * @param selectOption es el objeto que tiene un mapa de selectOptionItem por nombre de campo
     * @return una lista con los SelectOptionResult de cada entidad ordenada por prioridad
     */
    public static List<SelectOptionResult> getPriorityList(List<? extends BasicEntity> entityList,
                                                           SelectOption selectOption) {
        var results = new ArrayList<SelectOptionResult>();
        for (BasicEntity entity : entityList) {
            SelectOptionResult result = getResult(entity, selectOption);
            results.add(result);
        }
        results.sort((o1, o2) -> o2.getPriority() - o1.getPriority());  // greater priority first
        return results;
    }

    /**
     * Obtiene la prioridad de la entidad usando el so.
     * Para eso se buscan los campos que sean @priorizable y que existan en el so y que al aplicar la funcion del soi
     * se obtenga true.
     * Si el campo de la entidad existe en un SelectOptionItem (soi), se evalua la funcion incluida en el soi con el
     * valor incluido en el soi y el valor del campo de la entidad para ver si se incluir su prioridad en la suma.
     * @param entity la entidad a la que se le calcula la prioridad
     * @param so el SelectOption que contiene los nombres de campo como claves y los soi (SelectOptionItems) como valor
     * @return un SelectOptionResult conteniendo la prioridad y la entity
     */
    public static SelectOptionResult getResultAny(BasicEntity entity, SelectOption so) {
        SelectOptionResult result = new SelectOptionResult(0, entity);

        final List<Field> priorizableFields = Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.getAnnotation(Priorizable.class) != null).collect(Collectors.toList());
        for(Field priorizableField: priorizableFields){
            if(so.containsFieldName(priorizableField.getName())){
                int priority = matchAndGetPrioridad(priorizableField, entity, so);
                result.addPriority(priority);
            }
        }
        return result;
    }


    /**
     * Obtiene la prioridad de la entidad usando el so.
     * Para eso se buscan los campos que sean @priorizable y que existan en el so y que al aplicar la funcion del soi
     * se obtenga true.
     * Si el campo de la entidad existe en un SelectOptionItem (soi), se evalua la funcion incluida en el soi con el
     * valor incluido en el soi y el valor del campo de la entidad para ver si se incluir su prioridad en la suma.
     * @param entity la entidad a la que se le calcula la prioridad
     * @param so el SelectOption que contiene los nombres de campo como claves y los soi (SelectOptionItems) como valor
     * @return un SelectOptionResult conteniendo la prioridad y la entity
     */
    public static SelectOptionResult getResult(BasicEntity entity, SelectOption so) {
        SelectOptionResult result = new SelectOptionResult(0, entity);

        Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.getAnnotation(Priorizable.class) != null &&
                        so.containsFieldName(field.getName()) &&
                        so.get(field.getName()).matchEmpty(field, entity))
                .forEach(field -> result.addPriority(field.getAnnotation(Priorizable.class).priority()));
        return result;
    }

    /**
     * Devuelve el sor de mayor prioridad.
     * @param entityList es la lista de entidades
     * @param selectOption es el objeto que tiene un mapa de selectOptionItem por nombre de campo
     * @return el SelectOptionResult de mayor prioridad
     */
    public static Optional<SelectOptionResult> getFirst(List<? extends BasicEntity> entityList, SelectOption selectOption) {
        List<SelectOptionResult> list = getPriorityList(entityList, selectOption);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public static int matchAndGetPrioridad(Field field, Object entity, SelectOption so) {
        try{
            Object fieldValue = OBJECT_REFLECTION_UTIL.getFieldValueFromObject(entity, field.getName());
            if(fieldValue == null) {
                return ANY_PRIORITY;
            } else if(field.getType() == int.class && (int) fieldValue == 0) {
                return ANY_PRIORITY;
            } else if(fieldValue.equals(StringUtils.ANY)) {
                return ANY_PRIORITY;
            } else if(fieldValue.equals(StringUtils.EMPTY)) {
                return ANY_PRIORITY;
            } else if(so.get(field.getName()).match(field, entity)) {
                return field.getAnnotation(Priorizable.class).priority();
            }

            return NEGATIVE_PRIORITY;
        } catch (Exception ex) {
            return NO_PRIORITY;
        }
   }
}

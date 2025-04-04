package com.echevarne.sap.cloud.facturacion.transformacion.rules;

import com.echevarne.sap.cloud.facturacion.config.ContextProvider;
import org.springframework.core.convert.ConversionService;

import java.sql.Timestamp;
import java.util.Date;


/**
 * Transformador de tipo de datos.
 *
 * @author Steven Mendez
 */
public class TransformerTypeConverter {

    /**
     * Converts a data type to another
     */
    public static <T> T convert(Object origin, Class<T> toClass) {

        T target = null;

        if(origin != null){
            final ConversionService conversionService = ContextProvider.getBean(ConversionService.class);

            Class<?> originClass = origin.getClass();

            if (toClass.isAssignableFrom(originClass)) {
                target = (T) origin;
            } else if (conversionService.canConvert(originClass, toClass)){
                target = conversionService.convert(origin, toClass);
            } else if (origin.getClass() == Timestamp.class && toClass == Date.class) {
                target = (T) toDate((Timestamp) origin);
            } else if (origin.getClass() == Long.class && toClass == Timestamp.class) {
                target = (T) toTimestamp((Long) origin);
            } else if (origin.getClass() == Date.class && toClass == Timestamp.class) {
                target = (T) toTimestamp((Date) origin);
                throw new IllegalArgumentException("Type convertion from " + origin.getClass().getCanonicalName() + " to " + toClass.getCanonicalName() + " not supported");
            }
        }

        return target;
    }

    /**
     * Converts a String type to boolean.
     *
     * @param origin the origin
     * @return the boolean
     */
    private static Boolean toBoolean(String origin) {
        if (("|1|t|true|y|yes|").contains("|" + origin.toLowerCase() + "|"))
            return Boolean.TRUE;
        if (("|0|f|false|n|no|").contains("|" + origin.toLowerCase() + "|"))
            return Boolean.FALSE;

        throw new IllegalArgumentException("Value not parsable to Boolean " + origin);
    }

    /**
     * Converts an object type to string.
     *
     * @param value the value
     * @return the string
     */
    private static String toString(Object value) {
        return String.valueOf(value);
    }

    /**
     * Converts a Date type to timestamp.
     *
     * @param value the value
     * @return the timestamp
     */
    public static Timestamp toTimestamp(Date value) {
        return new Timestamp(value.getTime());
    }


    private static Date toDate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }

    /**
     * Converts a Long type To timestamp.
     *
     * @param value the value
     * @return the timestamp
     */
    public static Timestamp toTimestamp(Long value) {
        return new Timestamp(value);
    }

    /**
     * Converts a Integer type To int.
     *
     * @param value the value
     * @return the int
     */
    public static int toInt(Integer value) {
        return value.intValue();
    }

    /**
     * Compare to.
     *
     * @param class1 the class 1
     * @param value1 the value 1
     * @param value2 the value 2
     * @return the int
     * @throws Exception the exception
     */
    public static int compareTo(Class<? extends Object> class1, Object value1, Object value2) throws Exception {
        if (class1 == String.class)
            return ((String) value1).compareTo((String) value2);

        throw new Exception("ComprareTo Type convertion from " + class1.getClass().getCanonicalName() + " not supported");
    }

}

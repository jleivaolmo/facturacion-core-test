package com.echevarne.sap.cloud.facturacion.persistence;

import org.hibernate.LazyInitializationException;

import javax.persistence.EntityNotFoundException;
import java.util.function.Function;
import java.util.function.Supplier;

public class EntityUtil {

    public static <E, R> E getOrNull(Supplier<E> supplier, Function<E, R> nonIdFieldFetcherFunction){
        E value = null;
        try{
            final E getterValue = supplier.get();
            if (getterValue != null){
                // Will throw exception if the reference didn't exist
                nonIdFieldFetcherFunction.apply(getterValue);
            }
            value = getterValue;
        }catch(EntityNotFoundException| LazyInitializationException |NullPointerException e){
            // Nothing to do
        }
        return value;
    }
}

package com.echevarne.sap.cloud.facturacion.model.divisores;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD})
@Retention(RUNTIME)

public @interface SplitedBy {

    String field() default "";

    String code() default "";
}

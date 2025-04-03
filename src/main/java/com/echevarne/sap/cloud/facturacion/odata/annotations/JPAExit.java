package com.echevarne.sap.cloud.facturacion.odata.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RUNTIME)
public @interface JPAExit {
	boolean allowAll() default false;
	boolean allowEmpty() default false;
	String fieldId() default "";
	String fieldDescription() default "";
}

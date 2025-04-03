package com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD})
@Retention(RUNTIME)
public @interface ValueListParameterFilterOnly {
	
	String ValueListProperty() default "";
	
	
}

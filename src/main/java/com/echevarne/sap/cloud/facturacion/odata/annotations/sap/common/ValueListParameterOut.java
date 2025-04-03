package com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD})
@Retention(RUNTIME)
public @interface ValueListParameterOut {
	
	String ValueListProperty() default ""; //Path to property in the value list . Format is identical to PropertyPath annotations.
	String LocalDataProperty() default ""; //Path to property that is filled from response	
	
}

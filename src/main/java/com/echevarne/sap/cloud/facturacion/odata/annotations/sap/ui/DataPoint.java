package com.echevarne.sap.cloud.facturacion.odata.annotations.sap.ui;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD})
@Retention(RUNTIME)
public @interface DataPoint {
	
	String qualifier() default "";
	String title() default "";
	String description() default "";
	String criticallyField() default "";
	String criticallyValue() default "";
	
	boolean isHeaderFacet() default false;

}

package com.echevarne.sap.cloud.facturacion.odata.annotations.sap.ui;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD,TYPE})
@Retention(RUNTIME)
public @interface HeaderInfo {
	
	String singular() default "";
	String plural() default "";
	String image() default "";
	boolean title() default false;
	boolean description() default false;
	String titleText() default "";
	String descriptionText() default "";
		
}

package com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;

@Target({FIELD})
@Retention(RUNTIME)
public @interface ValueList {

	String Label();
	ValueListEntitiesEnum CollectionPath();	
	String CollectionRoot() default "";
	boolean SearchSupported() default false;
	ValueListParameter[] Parameters() default {};

}
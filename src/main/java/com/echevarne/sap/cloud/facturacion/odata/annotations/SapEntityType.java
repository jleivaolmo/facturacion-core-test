package com.echevarne.sap.cloud.facturacion.odata.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.SemanticsTypeEnum;


@Target({TYPE})
@Retention(RUNTIME)
public @interface SapEntityType {
		
	String label() default "";
	SemanticsTypeEnum semantics() default SemanticsTypeEnum.NONE;
	
}
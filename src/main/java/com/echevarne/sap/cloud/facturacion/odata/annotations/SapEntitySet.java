package com.echevarne.sap.cloud.facturacion.odata.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.SemanticsSetEnum;


@Target({TYPE})
@Retention(RUNTIME)
public @interface SapEntitySet {
	
	String label() default "";
	boolean creatable() default true;
	boolean updatable() default true;
	boolean deletable() default true;
	boolean searchable() default false;
	
	boolean pageable() default true;
	boolean topable() default true;
	boolean countable() default true;
	boolean addressable() default true;
	
	boolean requiresFilter() default false;
	boolean changeTracking() default false;
	String maxpagesize() default "";
	String deltaLinkValidity() default "";
	SemanticsSetEnum semantics() default SemanticsSetEnum.NONE;

}
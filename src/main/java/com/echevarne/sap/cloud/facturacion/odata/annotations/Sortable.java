package com.echevarne.sap.cloud.facturacion.odata.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FieldSortEnum;

@Target({FIELD})
@Retention(RUNTIME)
public @interface Sortable {
	
	int priority() default 1;
	FieldSortEnum order() default FieldSortEnum.Asc;
	
}

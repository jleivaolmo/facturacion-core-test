package com.echevarne.sap.cloud.facturacion.odata.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ParameterEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.SemanticsEnum;


@Target({FIELD})
@Retention(RUNTIME)
public @interface Sap {
		
	boolean sortable() default true;
	boolean filterable() default true;
	boolean creatable() default true;
	boolean updatable() default true;
	boolean searchable() default false;
	String text() default "";
	DisplayFormatEnum displayFormat() default DisplayFormatEnum.None;
	PropertyValueListEnum valueList() default PropertyValueListEnum.None;
	FilterRestrictionsEnum filterRestriction() default FilterRestrictionsEnum.None;
	boolean visible() default true;
	String hierarchyNodeFor() default "";
	String hierarchyNodeExternalKeyFor() default "";
	String hierarchyLevelFor() default "";
	String hierarchyParentNodeFor() default "";
	String hierarchyParentNavigationFor() default "";
	String hierarchyDrillStateFor() default "";
	String hierarchyNodeDescendantCountFor() default "";
	String hierarchyPreorderRankFor() default "";
	String hierarchySiblingRankFor() default "";
	String unit() default "";
	boolean requiredInFilter() default false;
	SemanticsEnum semantics() default SemanticsEnum.NONE;
	ParameterEnum parameter() default ParameterEnum.None;
	String fieldControl() default "";
	String aggregationRole() default "";
}
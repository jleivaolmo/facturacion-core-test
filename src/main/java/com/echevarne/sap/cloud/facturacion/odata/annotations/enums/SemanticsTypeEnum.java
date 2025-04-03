package com.echevarne.sap.cloud.facturacion.odata.annotations.enums;

public enum SemanticsTypeEnum {
	
	NONE(""),

	/**
	 * Entities of this type contain contact information following the vCard standard, 
	 * see values for sap:semantics on property level
	 */
	VCARD("vcard"), 
	
	/**
	 * Entities of this type contain event/appointment information following the iCalendar standard , 
	 * see values for sap:semantics on property level
	 */
	VEVENT("vevent"),
	
	/**
	 * This entity type represents parameters for another entity type to which it has a 
	 * collection-valued association
	 */
	VTODO("vtodo"),
	
	/**
	 * Entities of this type contain todo/task information following the iCalendar standard , 
	 * see values for sap:semantics on property level
	 */
	PARAMETERS("parameters"),
	
	/**
	 * Entity sets of a type with this semantics return result feeds with aggregated values for 
	 * properties annotated with sap:aggregation-role="measure" mentioned in the $select system query option. 
	 * The result consists of entities for all combinations of distinct values of all dimension properties 
	 * annotated with sap:aggregation-role="dimension" mentioned in the $select system query option of the request 
	 * matching the $filter expression. See also description of annotation sap:aggregation-role.
	 */
	AGGREGATE("aggregate"),
	
	/**
	 * This entity type represents query selection variants bundling parameter 
	 * selections and filter expressions for obtaining specific query results
	 */
	VARIANT("variant");
	
	public final String semantic;
	 
    private SemanticsTypeEnum(String semantic) {
        this.semantic = semantic;
    }
    
    public String toString() {
        return this.semantic;
    }
}
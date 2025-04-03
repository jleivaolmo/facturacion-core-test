package com.echevarne.sap.cloud.facturacion.odata.annotations.enums;

public enum SemanticsSetEnum {
	
	NONE(null),
	
	/**
	 * The entities of this set are automatically aggregated if the query option $select is specified. 
	 * Each property listed in $select is treated according to its aggregation role. 
	 * See description of attribute sap:semantics="aggregate" for Edm:EntityType below.
	 */
	AGGREGATE("aggregate"), 
	
	/**
	 * The entities of this set are snapshots of data that changes over time. 
	 * One of the key properties represents the temporal dimension.
	 */
	TIMESERIES("timeseries");
	
	public final String semantic;
	 
    private SemanticsSetEnum(String semantic) {
        this.semantic = semantic;
    }
    
    public String toString() {
        return this.semantic;
    }
}
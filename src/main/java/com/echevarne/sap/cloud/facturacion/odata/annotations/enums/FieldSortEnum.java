package com.echevarne.sap.cloud.facturacion.odata.annotations.enums;

public enum FieldSortEnum {
	
	Asc("asc"), 
	Desc("desc"); 
	
	public final String format;
	 
    private FieldSortEnum(String format) {
        this.format = format;
    }
    
    public String toString() {
        return this.format;
    }
}
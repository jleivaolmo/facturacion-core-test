package com.echevarne.sap.cloud.facturacion.odata.annotations.enums;

public enum FieldControlEnum {
    
    None("");
	
	public final String fieldControl;
	 
    private FieldControlEnum(String fieldControl) {
        this.fieldControl = fieldControl;
    }
    
    public String toString() {
        return this.fieldControl;
    }

}

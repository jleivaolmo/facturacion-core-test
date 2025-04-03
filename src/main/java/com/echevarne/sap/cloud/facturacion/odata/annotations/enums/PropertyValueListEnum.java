package com.echevarne.sap.cloud.facturacion.odata.annotations.enums;

public enum PropertyValueListEnum {
	
	None(""), // default
	Fixed("fixed-values"), // there is a short list of allowed field values that rarely changes over time
	Standard("standard"); // no restriction on number and volatility of allowed field values 
	
	public final String valueList;
	 
    private PropertyValueListEnum(String valueList) {
        this.valueList = valueList;
    }
    
    public String toString() {
        return this.valueList;
    }
}
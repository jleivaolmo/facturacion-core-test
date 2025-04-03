package com.echevarne.sap.cloud.facturacion.odata.annotations.enums;

public enum DisplayFormatEnum {
	
	None(""), // non specific format
	Date("Date"), // indicates that only the date part of an Edm.DateTime value is relevant
	DateTime("DateTime"), // indicates that only the date part of an Edm.DateTime value is relevant
	NonNegative("NonNegative"), // indicates that only non-negative numeric values are provided and persisted, other input will lead to errors. 
								//Intended for Edm.String fields that are internally stored as NUMC
	UpperCase("UpperCase"); //indicates that uppercase values are provided and persisted, lowercase input will be converted
	
	public final String format;
	 
    private DisplayFormatEnum(String format) {
        this.format = format;
    }
    
    public String toString() {
        return this.format;
    }
}
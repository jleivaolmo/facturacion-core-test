package com.echevarne.sap.cloud.facturacion.exception;

public enum ExceptionType {
	ENTITY_NOT_FOUND("not.found"), DUPLICATE_ENTITY("duplicate"),
	BUSINESS_EXCEPTION("business.exception"), ENTITY_EXCEPTION("exception"),
	ODATA_EXCEPTION("odata");

	String value;

	ExceptionType(String value) {
		this.value = value;
	}

	public String getValue() {
        return this.value;
	}
}
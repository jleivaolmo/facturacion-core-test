package com.echevarne.sap.cloud.facturacion.odata.annotations.enums;

public enum ParameterEnum {

    None(""), // No parameter
    Mandatory("mandatory"), // A value must be supplied for this parameter.
    Optional("optional"); // A value for this parameter can be left out by specifying an empty string
    // (applicable only for parameter properties of type Edm.String).
    // For parameters of other types, the default value conveyed in the metadata
    // should be assigned, if the parameter shall be omitted.

    public final String parameter;
	 
    private ParameterEnum(String parameter) {
        this.parameter = parameter;
    }
    
    public String toString() {
        return this.parameter;
    }

}

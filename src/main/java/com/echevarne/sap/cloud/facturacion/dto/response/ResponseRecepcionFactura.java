package com.echevarne.sap.cloud.facturacion.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseRecepcionFactura {

    @JsonProperty
	boolean success;
    
    @JsonProperty
    String message;

}

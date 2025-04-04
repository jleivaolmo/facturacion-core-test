package com.echevarne.sap.cloud.facturacion.services;

import org.springframework.http.ResponseEntity;
import com.echevarne.sap.cloud.facturacion.dto.response.Response;

public interface ActualizarPeticionService {
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity<Response> callTransformar(String codigoPeticion);

}

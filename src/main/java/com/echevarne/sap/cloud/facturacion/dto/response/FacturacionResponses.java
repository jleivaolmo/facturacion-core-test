package com.echevarne.sap.cloud.facturacion.dto.response;

import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.services.MessagesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FacturacionResponses {

	private static MessagesService messagesSrv;

    @Autowired
	public FacturacionResponses(MessagesService messagesSrv) {
		FacturacionResponses.messagesSrv = messagesSrv;
	}
    
    /**
	 * 
	 * @param <T>
	 * @param body
	 * @param msg
	 * @param args
	 * @return
	 */
	public static <T> ResponseEntity<Response<?>> buildResponse(T body, String msg, String... args) {
		Response<T> response = new Response<T>(messagesSrv).success(msg, ConstFacturacion.IDIOMA_DEFAULT.toLowerCase(), args).setResponseBody(body);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	public static <T> ResponseEntity<Response<?>> buildResponseError(ResponseError error) {
		Response<T> response = new Response<T>(messagesSrv).exception();
		response.setErrors(error);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}

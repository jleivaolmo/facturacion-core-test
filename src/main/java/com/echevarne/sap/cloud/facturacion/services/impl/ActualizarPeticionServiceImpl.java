package com.echevarne.sap.cloud.facturacion.services.impl;

import static com.echevarne.sap.cloud.facturacion.util.Destinations.Enum.TRANSFORMACION;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.echevarne.sap.cloud.facturacion.dto.response.Response;
import com.echevarne.sap.cloud.facturacion.services.ActualizarPeticionService;
import com.echevarne.sap.cloud.facturacion.util.Destinations;

@Service("actualizarPeticionSrv")
@AllArgsConstructor
public class ActualizarPeticionServiceImpl implements ActualizarPeticionService {

	private static final String TRANSFORMACION_URI = "/peticiones/Transformar/";

	private final Destinations destinations;

	@SuppressWarnings("rawtypes")
	@Override
	public ResponseEntity<Response> callTransformar(String codigoPeticion) {
		String url = TRANSFORMACION_URI + codigoPeticion;
		RestTemplate restTemplate = destinations.createRestTemplate(TRANSFORMACION);
		return restTemplate.postForEntity(url, codigoPeticion, Response.class);
	}

}

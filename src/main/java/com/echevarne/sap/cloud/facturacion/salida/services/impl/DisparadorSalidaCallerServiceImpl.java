package com.echevarne.sap.cloud.facturacion.salida.services.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.echevarne.sap.cloud.facturacion.resources.BaseResource;
import com.echevarne.sap.cloud.facturacion.resources.PeriodosFacturadosResource;
import com.echevarne.sap.cloud.facturacion.salida.services.DisparadorSalidaCallerService;
import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("disparadorSalidaCallerService")
public class DisparadorSalidaCallerServiceImpl implements DisparadorSalidaCallerService {

	@Value("${ms.salida.url}")
	private String msSalidaUrl;

	private static final String URL_REPORTE = "/reporteFacturacion";
	private static final String URL_PETICION = "/peticionValorada";
	private static final String URL_PRESUPUESTO = "";	// TODO : completar

	ExecutorService executor = Executors.newFixedThreadPool(5);

	@Override
	public void callDisparadorSalidaPeticionValorada(Long idTrazabiliddad) {
		// var resource = new BaseResource();
		// resource.setIdObjeto(idTrazabiliddad);
		// callDisparadorSalidaAsync(msSalidaUrl + URL_PETICION, resource);
	}

	// @Override
	// public void callDisparadorSalidaPresupuesto(String codigoPeticion) {
	// 	var resource = new PresupuestosResource();
	// 	resource.setCodigoPeticion(codigoPeticion);
	// 	callDisparadorSalidaAsync(msSalidaUrl + URL_PRESUPUESTO, resource);
	// }

	private void callDisparadorSalidaAsync(String url, BaseResource resource) {
		// executor.execute(() -> {
		// 	try {
		// 		log.debug("Calling url: " + url);
		// 		RestTemplate restTemplate = Destinations.createRestTemplate(SALIDA);
		// 		restTemplate.postForEntity(url, resource, String.class);
		// 	}
		// 	catch (Exception ex) {
		// 		log.error(ex.getMessage());
		// 	}
		// });
	}
}

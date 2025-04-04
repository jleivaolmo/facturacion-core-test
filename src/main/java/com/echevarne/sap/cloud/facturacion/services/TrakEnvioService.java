package com.echevarne.sap.cloud.facturacion.services;

import java.util.Date;
import java.util.Set;

import com.echevarne.sap.cloud.facturacion.dto.RequestMethod;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;

public interface TrakEnvioService {

	public void registrarFacturas(String codigoPedido, String numeroFactura, Date facturaDate, String indicador, Set<String> codigosPeticion,
			String rectificativa);

	public void enviarFacturas(String codigoPedido, String numeroFactura, Date facturaDate, String indicador, String codigoPeticion, String rectificativa)
			throws Exception;

	public void enviarPeticionValorada(RequestMethod request) throws Exception;

	public void registrarPeticionValorada(SolicitudIndividual si);
}

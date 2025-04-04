package com.echevarne.sap.cloud.facturacion.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.echevarne.sap.cloud.facturacion.agrupador.AgrupadorDto;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolicitudesAgrupadas;

public interface AgrupadorItemsService {
	
	public SolicitudesAgrupadas createSolicitudesAgrupadas(AgrupadorDto key);

	public int compactarIdItems(Long idSolAgr, List<BigInteger> idsForGrouping, int itemCount, Map<String, Integer> nuevasPosiciones) throws Exception;

}

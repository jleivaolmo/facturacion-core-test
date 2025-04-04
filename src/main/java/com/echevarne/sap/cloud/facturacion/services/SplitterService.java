package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.divisores.Splittable;

/**
 * Class for services {@link SplitterService}.
 * 
 * <p>Services for the bussiness logic of the Model: SolicitudesAgrupadas</p>
 *
 */
public interface SplitterService {
	
	public final String DEFAULT_WITH_RULE = "*";

    String getSplitterKey(String idRegla, Splittable entity, Splittable reglaForFind);

}

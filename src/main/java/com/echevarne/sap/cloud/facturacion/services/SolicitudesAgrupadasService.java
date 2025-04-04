package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolicitudesAgrupadas;

import java.util.List;
import java.util.Optional;

/**
 * Class for services {@link SolicitudesAgrupadasService}.
 * 
 * <p>
 * . . .
 * </p>
 * <p>
 * Services for the bussiness logic of the Model: SolicitudesAgrupadas
 * </p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
public interface SolicitudesAgrupadasService extends CrudService<SolicitudesAgrupadas, Long> {

	Optional<List<SolicitudesAgrupadas>> findByBillingDocument(String billingDocument);

	Optional<List<SolicitudesAgrupadas>> findByBillingDocumentAndSoldToParty(String billingDocument,
			String soldToParty);
	
	Optional<List<SolicitudesAgrupadas>> findBySalesOrderType(String salesOrderType);
}

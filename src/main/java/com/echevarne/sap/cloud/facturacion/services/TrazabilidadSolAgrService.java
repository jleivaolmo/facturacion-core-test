package com.echevarne.sap.cloud.facturacion.services;

import java.util.List;
import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudAgrupado;

/**
 * Class for services {@link TrazabilidadSolAgrService}.
 * 
 * <p>. . .</p>
 * <p>Services for the bussiness logic of the Model: TrazabilidadSolicitud</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
public interface TrazabilidadSolAgrService extends CrudService<TrazabilidadSolicitudAgrupado, Long> {
	
	Optional<List<TrazabilidadSolicitudAgrupado>> findAllBySalesOrder(String salesOrder);
		
	Optional<List<TrazabilidadSolicitudAgrupado>> findAllByBillingDocument(String billingDocument);
		
	Optional<List<TrazabilidadSolicitudAgrupado>> findAllByTrazabilidadOrderByEntityCreationTimestamp(TrazabilidadSolicitud traza);
}
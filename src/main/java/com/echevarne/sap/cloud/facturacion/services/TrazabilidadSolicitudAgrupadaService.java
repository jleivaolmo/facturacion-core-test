package com.echevarne.sap.cloud.facturacion.services;

import java.util.List;
import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudAgrupada;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudAgrupado;

/**
 * Class for services {@link TrazabilidadSolicitudAgrupada}.
 * <p>
 * Services for the bussiness logic of the Model: TrazabilidadSolicitudAgrupada
 * </p>
 *
 * @author David Bolet
 * @since 29/04/2021
 */
public interface TrazabilidadSolicitudAgrupadaService extends CrudService<TrazabilidadSolicitudAgrupado, Long> {

	Optional<List<TrazabilidadSolicitudAgrupado>> findAllByBillingDocument(String billingDocument);
}
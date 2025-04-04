package com.echevarne.sap.cloud.facturacion.services;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.conversion.ConversionOficinaVentas;
import com.echevarne.sap.cloud.facturacion.model.conversion.ConversionOrganizacionVentas;

/**
 * Class for services {@link ConversionOficinaVentasService}.
 *
 * <p>Services for the bussiness logic of the Model: ConversionOficinaVentas</p>
 */
public interface ConversionOficinaVentasService extends CrudService<ConversionOficinaVentas, Long> {

    Optional<ConversionOficinaVentas> findByTrak(String trak);

}

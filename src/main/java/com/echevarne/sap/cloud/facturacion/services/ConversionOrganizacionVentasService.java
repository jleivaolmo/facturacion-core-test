package com.echevarne.sap.cloud.facturacion.services;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.conversion.ConversionOrganizacionVentas;
import com.echevarne.sap.cloud.facturacion.model.conversion.ConversionSociedad;

/**
 * Class for services {@link ConversionOrganizacionVentasService}.
 * 
 * <p>Services for the bussiness logic of the Model: ConversionOrganizacionVentas</p>
 */
public interface ConversionOrganizacionVentasService extends CrudService<ConversionOrganizacionVentas, Long> {

	Optional<ConversionOrganizacionVentas> findByParams(String sector, String codigoDelegacion);

}

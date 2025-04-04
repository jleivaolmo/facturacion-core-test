package com.echevarne.sap.cloud.facturacion.services;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.conversion.ConversionSector;
import com.echevarne.sap.cloud.facturacion.model.conversion.ConversionSociedad;

/**
 * Class for services {@link ConversionSociedadService}.
 * 
 * <p>Services for the bussiness logic of the Model: ConversionSociedad</p>
 */
public interface ConversionSociedadService extends CrudService<ConversionSociedad, Long> {
	
	Optional<ConversionSociedad> findByParams(String cliente, String codigoDelegacion, String prueba, String sector);

}

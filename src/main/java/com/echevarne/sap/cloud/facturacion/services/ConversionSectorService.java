package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.contratos.ContratoCapitativo;
import com.echevarne.sap.cloud.facturacion.model.conversion.ConversionSector;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Class for services {@link ConversionSectorService}.
 *
 * <p>Services for the bussiness logic of the Model: ConversionSector</p>
 */
public interface ConversionSectorService extends CrudService<ConversionSector, Long> {

	Optional<ConversionSector> findByTipoPeticion(int tipoPeticion);
}

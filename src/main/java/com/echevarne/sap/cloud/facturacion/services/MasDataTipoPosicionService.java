package com.echevarne.sap.cloud.facturacion.services;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTipoPosicion;

/**
 * Business Services logic for Model {@link MasDataTipoPosicion}
 * 
 * @author Hernan Girardi
 * @since 26/03/2020
 */
public interface MasDataTipoPosicionService
		extends CrudService<MasDataTipoPosicion, Long>, MasDataBaseService<MasDataTipoPosicion, Long> {

	Optional<MasDataTipoPosicion> findByTipoPosicion(String tipoPosicion);
}

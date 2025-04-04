package com.echevarne.sap.cloud.facturacion.services;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosRectificacion;

/**
 * Business Services logic for Model: {@link MasDataMotivosRectificacion}
 * @author Hernan Girardi
 * @since 25/08/2020
 */
public interface MasDataMotivosRectificacionService
		extends CrudService<MasDataMotivosRectificacion, Long>, MasDataBaseService<MasDataMotivosRectificacion, Long> {

	Optional<MasDataMotivosRectificacion> findByCodigo(String codigo);

}

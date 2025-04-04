package com.echevarne.sap.cloud.facturacion.services;

import java.util.Optional;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosCancelacion;

/**
 * Business Services logic for Model: {@link MasDataMotivosRectificacion}
 * @author Hernan Girardi
 * @since 25/08/2020
 */
public interface MasDataMotivosCancelacionService
		extends CrudService<MasDataMotivosCancelacion, Long>, MasDataBaseService<MasDataMotivosCancelacion, Long> {

	Optional<MasDataMotivosCancelacion> findByCodigo(String codigo);

}

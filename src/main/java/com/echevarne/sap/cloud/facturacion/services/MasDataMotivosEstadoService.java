package com.echevarne.sap.cloud.facturacion.services;

import java.util.List;
import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataAlerta;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;

/**
 * Business Services logic for Model: {@link MasDataMotivosEstado}
 * @author Hernan Girardi
 * @since 25/08/2020
 */
public interface MasDataMotivosEstadoService extends CrudService<MasDataMotivosEstado, Long>, MasDataBaseService<MasDataMotivosEstado, Long> {
	
	Optional<MasDataMotivosEstado> findByAlerta(MasDataAlerta alerta);
	
	Optional<MasDataMotivosEstado> findByAlertaCodigoAlerta(String codigoAlerta);
	
	List<MasDataMotivosEstado> findByDescripcionContains(String descripciontoFind);
	
	//para buscar un motivo por un texto Like '%descripciontoFind%' y una Alerta o Alerta is NULL
	List<MasDataMotivosEstado> findByAlertaAndDescripcionContains(MasDataAlerta alerta, String descripciontoFind);

	Optional<MasDataMotivosEstado> findByCodigoAndActive(String codigoEstado, boolean active);
	
}

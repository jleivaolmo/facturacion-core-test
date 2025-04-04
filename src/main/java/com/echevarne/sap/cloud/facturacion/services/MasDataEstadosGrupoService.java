package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstadosGrupo;

/**
 * Business Services logic for Model: {@link MasDataEstadosGrupo}
 */
public interface MasDataEstadosGrupoService extends CrudService<MasDataEstadosGrupo, Long>,
		MasDataBaseService<MasDataEstadosGrupo, Long> {

	MasDataEstadosGrupo findByCodigo(String codigo);
}

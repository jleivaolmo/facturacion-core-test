package com.echevarne.sap.cloud.facturacion.services;

import java.util.List;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;

/**
 * Business Services logic for Model: {@link MasDataEstado}
 * @author Hernan Girardi
 * @since 26/03/2020
 */
public interface MasDataEstadoService extends CrudService<MasDataEstado, Long>, MasDataBaseService<MasDataEstado, Long> {

	MasDataEstado findByCodeEstado(String codeEstado);

	MasDataEstado getEstado(String codeEstado);

	List<String> findDistinctCodeEstadoByActive(boolean active);

	List<String> findDistinctCodeEstado();
}

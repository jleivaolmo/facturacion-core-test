package com.echevarne.sap.cloud.facturacion.services;

import java.util.List;
import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.entities.ConfiguracionFieldStatus;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstadosGrupo;

public interface ConfiguracionFieldStatusService extends CrudService<ConfiguracionFieldStatus, Long> {

	Optional<ConfiguracionFieldStatus> findByEntityNameFieldLevelAndState(String entityName, String fieldName,
			String level, MasDataEstadosGrupo state);

    Optional<List<ConfiguracionFieldStatus>> findAllActives();

	List<ConfiguracionFieldStatus> findAll();
}

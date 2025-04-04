package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.entities.ConfiguracionFieldStatus;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstadosGrupo;
import com.echevarne.sap.cloud.facturacion.repositories.ConfiguracionFieldStatusRep;
import com.echevarne.sap.cloud.facturacion.services.ConfiguracionFieldStatusService;


/**
 *
 * @author davidbolet
 *
 */
@Service("configuracionFieldStatusService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ConfiguracionFieldStatusServiceImpl extends CrudServiceImpl<ConfiguracionFieldStatus, Long> implements ConfiguracionFieldStatusService {

	private final ConfiguracionFieldStatusRep configuracionFieldStatusRep;

	@Autowired
	public ConfiguracionFieldStatusServiceImpl(final ConfiguracionFieldStatusRep configuracionFieldStatusRep) {
		super(configuracionFieldStatusRep);
		this.configuracionFieldStatusRep = configuracionFieldStatusRep;
	}

	@Override
	public Optional<ConfiguracionFieldStatus> findByEntityNameFieldLevelAndState(String entityName, String fieldName, String level, MasDataEstadosGrupo state) {
		return configuracionFieldStatusRep.findByEntidadNombreNivelYEstado(entityName, fieldName, level, state);
	}

	@Override
	public Optional<List<ConfiguracionFieldStatus>> findAllActives() {
		return configuracionFieldStatusRep.findByInactive(false);
	}

	@Override
	public List<ConfiguracionFieldStatus> findAll() {
		return configuracionFieldStatusRep.findAll();
	}

}

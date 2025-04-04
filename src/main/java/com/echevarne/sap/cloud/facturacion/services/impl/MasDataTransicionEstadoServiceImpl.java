package com.echevarne.sap.cloud.facturacion.services.impl;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataTransicionEstado;
import com.echevarne.sap.cloud.facturacion.repositories.MasDataTransicionEstadoRep;
import com.echevarne.sap.cloud.facturacion.services.MasDataTransicionEstadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Business Service implementation of {@link MasDataTransicionEstadoService}
 */
@Service("masDataEstadosSecSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MasDataTransicionEstadoServiceImpl extends CrudServiceImpl<MasDataTransicionEstado, Long>
		implements MasDataTransicionEstadoService {

	private final MasDataTransicionEstadoRep masDatatransicionEstadosRep;

	@Autowired
	public MasDataTransicionEstadoServiceImpl(MasDataTransicionEstadoRep masDatatransicionEstadosRep) {
		super(masDatatransicionEstadosRep);
		this.masDatatransicionEstadosRep = masDatatransicionEstadosRep;
	}

	@Override
	public List<MasDataTransicionEstado> findByActive(boolean active) {
		return masDatatransicionEstadosRep.findByActive(active);
	}

	@Override
	public List<MasDataTransicionEstado> findAllByOrigenAndAutomatico(MasDataEstado origen, boolean automatico) {
		return this.masDatatransicionEstadosRep.findAllByOrigenAndAutomatico(origen, automatico);
	}

	@Override
	public void reloadData() {}

	@Override
	public List<MasDataTransicionEstado> findAllActiveByOrigenCodeAndDestinoCode(MasDataEstado estadoOrigen, List<String> estadosDestino) {
		return masDatatransicionEstadosRep.findAllByOrigenAndActiveAndDestinoCodeEstado(estadoOrigen.getCodeEstado(), true, estadosDestino);
	}
	
	@Override
	public List<MasDataTransicionEstado> findAllActiveByOrigenCodeAndDestinoCode(String estadoOrigen, List<String> estadosDestino) {
		return masDatatransicionEstadosRep.findAllByOrigenAndActiveAndDestinoCodeEstado(estadoOrigen, true, estadosDestino);
	}

}

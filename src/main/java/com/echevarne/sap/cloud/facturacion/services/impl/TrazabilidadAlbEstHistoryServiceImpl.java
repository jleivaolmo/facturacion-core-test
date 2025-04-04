package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadAlbaranEstHistory;
import com.echevarne.sap.cloud.facturacion.repositories.TrazabilidadAlbEstHistoryRep;
import com.echevarne.sap.cloud.facturacion.services.TrazabilidadAlbEstHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("trazabilidadAlbEstHistorySrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TrazabilidadAlbEstHistoryServiceImpl extends CrudServiceImpl<TrazabilidadAlbaranEstHistory, Long> implements TrazabilidadAlbEstHistoryService  {

	private final TrazabilidadAlbEstHistoryRep repo;

	@Autowired
	public TrazabilidadAlbEstHistoryServiceImpl(TrazabilidadAlbEstHistoryRep repo) {
		super(repo);
		this.repo = repo;
	}

	public Optional<TrazabilidadAlbaranEstHistory> findByEstado(MasDataEstado estado){
		return repo.findByEstado(estado);
	}

}

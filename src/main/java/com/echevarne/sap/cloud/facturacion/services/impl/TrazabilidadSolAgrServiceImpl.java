package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudAgrupado;
import com.echevarne.sap.cloud.facturacion.repositories.TrazabilidadSolAgrRepository;
import com.echevarne.sap.cloud.facturacion.services.TrazabilidadSolAgrService;

@Service("trazabilidadSolAgrSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TrazabilidadSolAgrServiceImpl extends CrudServiceImpl<TrazabilidadSolicitudAgrupado, Long> implements TrazabilidadSolAgrService {

	private final TrazabilidadSolAgrRepository repo;

	@Autowired
	public TrazabilidadSolAgrServiceImpl(final TrazabilidadSolAgrRepository repo) {
		super(repo);
		this.repo = repo;
	}

	@Override
	public Optional<List<TrazabilidadSolicitudAgrupado>> findAllBySalesOrder(String salesOrder) {
		return repo.findAllBySalesOrder(salesOrder);
	}

	@Override
	public Optional<List<TrazabilidadSolicitudAgrupado>> findAllByBillingDocument(String billingDocument) {
		return repo.findAllByBillingDocument(billingDocument);
	}

	@Override
	public Optional<List<TrazabilidadSolicitudAgrupado>> findAllByTrazabilidadOrderByEntityCreationTimestamp(TrazabilidadSolicitud traza) {
		 return repo.findAllByTrazabilidadOrderByEntityCreationTimestamp(traza);
	}
}

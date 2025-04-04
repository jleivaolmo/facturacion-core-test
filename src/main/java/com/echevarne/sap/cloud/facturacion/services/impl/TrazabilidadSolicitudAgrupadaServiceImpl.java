package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudAgrupado;
import com.echevarne.sap.cloud.facturacion.repositories.TrazabilidadSolicitudAgrupadaRepository;
import com.echevarne.sap.cloud.facturacion.services.TrazabilidadSolicitudAgrupadaService;

@Service("trazabilidadSolicitudAgrupadaSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TrazabilidadSolicitudAgrupadaServiceImpl extends CrudServiceImpl<TrazabilidadSolicitudAgrupado, Long> implements TrazabilidadSolicitudAgrupadaService {

	private final TrazabilidadSolicitudAgrupadaRepository trazabilidadSolicitudAgrupadaRepository;
	
	@Autowired
	public TrazabilidadSolicitudAgrupadaServiceImpl(final TrazabilidadSolicitudAgrupadaRepository repo) {
		super(repo);
		this.trazabilidadSolicitudAgrupadaRepository = repo;
	}

	@Override
	public Optional<List<TrazabilidadSolicitudAgrupado>> findAllByBillingDocument(String billingDocument) {
		return this.trazabilidadSolicitudAgrupadaRepository.findAllByBillingDocument(billingDocument);
	}	
}

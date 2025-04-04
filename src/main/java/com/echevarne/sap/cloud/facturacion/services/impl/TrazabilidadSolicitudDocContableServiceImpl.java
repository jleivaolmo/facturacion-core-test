package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudDocContable;
import com.echevarne.sap.cloud.facturacion.repositories.TrazabilidadSolicitudDocContableRepository;
import com.echevarne.sap.cloud.facturacion.services.TrazabilidadSolicitudDocContableService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TrazabilidadSolicitudDocContableServiceImpl
		extends CrudServiceImpl<TrazabilidadSolicitudDocContable, Long>
		implements TrazabilidadSolicitudDocContableService {

	private final TrazabilidadSolicitudDocContableRepository repo;

	@Autowired
	public TrazabilidadSolicitudDocContableServiceImpl(final TrazabilidadSolicitudDocContableRepository repo) {
		super(repo);
		this.repo = repo;
	}

	@Override
	public boolean exists(TrazabilidadSolicitud traza, TrazabilidadSolicitudDocContable.TipoOperacion cob) {
		return false;
	}

	@Override
	public List<TrazabilidadSolicitudDocContable> findByTrazabilidadAndTipoOperacion(
			TrazabilidadSolicitud traza, TrazabilidadSolicitudDocContable.TipoOperacion tipoOperacion) {
		return repo.findByTrazabilidadAndTipoOperacion(traza, tipoOperacion);
	}

}

package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudProcessStatus;
import com.echevarne.sap.cloud.facturacion.repositories.TrazabilidadSolicitudProcessStatusRep;
import com.echevarne.sap.cloud.facturacion.services.TrazabilidadSolicitudProcessStatusService;

@Service("trzSolProcStatusSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TrazabilidadSolicitudProcessStatusServiceImpl
		extends CrudServiceImpl<TrazabilidadSolicitudProcessStatus, Long> implements TrazabilidadSolicitudProcessStatusService {

	@Autowired
	public TrazabilidadSolicitudProcessStatusServiceImpl(final TrazabilidadSolicitudProcessStatusRep trzSolProcStatusRep) {
		super(trzSolProcStatusRep);
	}
}

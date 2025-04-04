package com.echevarne.sap.cloud.facturacion.services.impl;


import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.repositories.TrazabilidadSolicitudRepository;
import com.echevarne.sap.cloud.facturacion.services.TrazabilidadSolicitudService;

@Service("trazabilidadSolicitudSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TrazabilidadSolicitudServiceImpl extends CrudServiceImpl<TrazabilidadSolicitud, Long> implements TrazabilidadSolicitudService {

	private final TrazabilidadSolicitudRepository repo;

	@Autowired
	public TrazabilidadSolicitudServiceImpl(final TrazabilidadSolicitudRepository repo){
		super(repo);
		this.repo = repo;
	}
	@Override
	public TrazabilidadSolicitud findByPeticionMuestreo(PeticionMuestreo pm) {
		return repo.findByPeticionRec(pm);
	}

}

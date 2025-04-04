package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesEstado;
import com.echevarne.sap.cloud.facturacion.repositories.PetMuesEstadoRep;
import com.echevarne.sap.cloud.facturacion.services.PetMuesEstadoService;

@Service("petMuesEstadoSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PetMuesEstadoServiceImpl extends CrudServiceImpl<PetMuesEstado, Long> implements PetMuesEstadoService {

	@Autowired
	public PetMuesEstadoServiceImpl(final PetMuesEstadoRep petMuesEstadoRep){
		super(petMuesEstadoRep);
	}
}

package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesOperacion;
import com.echevarne.sap.cloud.facturacion.repositories.PetMuesOperacionRep;
import com.echevarne.sap.cloud.facturacion.services.PetMuesOperacionService;

@Service("petMuesOperacionSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PetMuesOperacionServiceImpl extends CrudServiceImpl<PetMuesOperacion, Long>
		implements PetMuesOperacionService {
	@Autowired
	public PetMuesOperacionServiceImpl(final PetMuesOperacionRep petMuesOperacionRep){
		super(petMuesOperacionRep);
	}
}

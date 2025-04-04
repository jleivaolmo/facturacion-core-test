package com.echevarne.sap.cloud.facturacion.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesMedico;
import com.echevarne.sap.cloud.facturacion.repositories.PetMuesMedicoRep;
import com.echevarne.sap.cloud.facturacion.services.PetMuesMedicoService;

@Service("petMuesMedicoSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PetMuesMedicoServiceImpl extends CrudServiceImpl<PetMuesMedico, Long>
		implements PetMuesMedicoService {
	@Autowired
	public PetMuesMedicoServiceImpl(final PetMuesMedicoRep petMuesMedicoRep){
		super(petMuesMedicoRep);
	}
}

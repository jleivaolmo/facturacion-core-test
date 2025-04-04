package com.echevarne.sap.cloud.facturacion.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesAlerta;
import com.echevarne.sap.cloud.facturacion.repositories.PetMuesAlertaRep;
import com.echevarne.sap.cloud.facturacion.services.PetMuesAlertaService;

@Service("petMuesAlertaSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PetMuesAlertaServiceImpl extends CrudServiceImpl<PetMuesAlerta, Long>
		implements PetMuesAlertaService {
	@Autowired
	public PetMuesAlertaServiceImpl(final PetMuesAlertaRep petMuesAlertaRep){
		super(petMuesAlertaRep);
	}
}

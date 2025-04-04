package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrTexts;
import com.echevarne.sap.cloud.facturacion.repositories.SolAgrTextsRep;
import com.echevarne.sap.cloud.facturacion.services.SolAgrTextsService;

@Service("solAgrTextsSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SolAgrTextsServiceImpl extends CrudServiceImpl<SolAgrTexts, Long>
		implements SolAgrTextsService {

	@Autowired
	public SolAgrTextsServiceImpl(final SolAgrTextsRep solAgrTextsRep) {
		super(solAgrTextsRep);
	}
}

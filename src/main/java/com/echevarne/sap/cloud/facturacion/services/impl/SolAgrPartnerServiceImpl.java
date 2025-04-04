package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrPartner;
import com.echevarne.sap.cloud.facturacion.repositories.SolAgrPartnerRep;
import com.echevarne.sap.cloud.facturacion.services.SolAgrPartnerService;

@Service("solAgrPartnerSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SolAgrPartnerServiceImpl extends CrudServiceImpl<SolAgrPartner, Long>
implements SolAgrPartnerService {

	@Autowired
	public SolAgrPartnerServiceImpl(final SolAgrPartnerRep solAgrPartnerRep){
		super(solAgrPartnerRep);
	}

}

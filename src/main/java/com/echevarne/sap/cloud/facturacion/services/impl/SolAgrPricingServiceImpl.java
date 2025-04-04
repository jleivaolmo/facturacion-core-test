package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrPricing;
import com.echevarne.sap.cloud.facturacion.repositories.SolAgrPricingRep;
import com.echevarne.sap.cloud.facturacion.services.SolAgrPricingService;

@Service("solAgrPricingSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SolAgrPricingServiceImpl extends CrudServiceImpl<SolAgrPricing, Long>
implements SolAgrPricingService {

	@Autowired
	public SolAgrPricingServiceImpl(final SolAgrPricingRep solAgrPricingRep){
		super(solAgrPricingRep);
	}
}

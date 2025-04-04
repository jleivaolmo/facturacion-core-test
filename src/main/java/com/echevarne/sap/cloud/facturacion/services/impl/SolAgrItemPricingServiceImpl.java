package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItemPricing;
import com.echevarne.sap.cloud.facturacion.repositories.SolAgrItemPricingRep;
import com.echevarne.sap.cloud.facturacion.services.SolAgrItemPricingService;

@Service("solAgrItemPricingSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SolAgrItemPricingServiceImpl extends CrudServiceImpl<SolAgrItemPricing, Long>
        implements SolAgrItemPricingService {

    @Autowired
    public SolAgrItemPricingServiceImpl(final SolAgrItemPricingRep solAgrItemPricingRep) {
        super(solAgrItemPricingRep);
    }
}

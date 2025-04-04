package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndPricing;
import com.echevarne.sap.cloud.facturacion.repositories.SolIndPricingRepository;
import com.echevarne.sap.cloud.facturacion.services.SolIndPricingService;

@Service("solIndPricingService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SolIndPricingServiceImpl extends CrudServiceImpl<SolIndPricing, Long> implements SolIndPricingService {

    @Autowired
    public SolIndPricingServiceImpl(final SolIndPricingRepository repo){
        super(repo);
    }
}

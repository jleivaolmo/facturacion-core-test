package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItemPartner;
import com.echevarne.sap.cloud.facturacion.repositories.SolIndItemPartnerRepository;
import com.echevarne.sap.cloud.facturacion.services.SolIndItemPartnerService;

@Service("solIndItemPartnerService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SolIndItemPartnerServiceImpl extends CrudServiceImpl<SolIndItemPartner, Long>
        implements SolIndItemPartnerService {

    @Autowired
    public SolIndItemPartnerServiceImpl(final SolIndItemPartnerRepository repo){
        super(repo);
    }
}

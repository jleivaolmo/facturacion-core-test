package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItemPartner;
import com.echevarne.sap.cloud.facturacion.repositories.SolAgrItemPartnerRep;
import com.echevarne.sap.cloud.facturacion.services.SolAgrItemPartnerService;

@Service("solAgrItemPartnerSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SolAgrItemPartnerServiceImpl extends CrudServiceImpl<SolAgrItemPartner, Long>
        implements SolAgrItemPartnerService {

    @Autowired
    public SolAgrItemPartnerServiceImpl(final SolAgrItemPartnerRep solAgrItemPartnerRep) {
        super(solAgrItemPartnerRep);
    }
}

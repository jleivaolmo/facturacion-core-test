package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItems;
import com.echevarne.sap.cloud.facturacion.repositories.SolAgrItemsRep;
import com.echevarne.sap.cloud.facturacion.services.SolAgrItemsService;

@Service("solAgrItemsSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SolAgrItemsServiceImpl extends CrudServiceImpl<SolAgrItems, Long>
        implements SolAgrItemsService {

    @Autowired
    public SolAgrItemsServiceImpl(final SolAgrItemsRep solAgrItemsRep) {
        super(solAgrItemsRep);
    }

}

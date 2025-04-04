package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolAgrItemTexts;
import com.echevarne.sap.cloud.facturacion.repositories.SolAgrItemTextsRep;
import com.echevarne.sap.cloud.facturacion.services.SolAgrItemTextsService;

@Service("solAgrItemTextsSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SolAgrItemTextsServiceImpl extends CrudServiceImpl<SolAgrItemTexts, Long>
        implements SolAgrItemTextsService {

    @Autowired
    public SolAgrItemTextsServiceImpl(final SolAgrItemTextsRep solAgrItemTextsRep){
        super(solAgrItemTextsRep);
    }

}

package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndTexts;
import com.echevarne.sap.cloud.facturacion.repositories.SolIndTextsRepository;
import com.echevarne.sap.cloud.facturacion.services.SolIndTextsService;

@Service("solIndTextsService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SolIndTextsServiceImpl extends CrudServiceImpl<SolIndTexts, Long> implements SolIndTextsService {

    @Autowired
    public SolIndTextsServiceImpl(final SolIndTextsRepository repo){
        super(repo);
    }
}

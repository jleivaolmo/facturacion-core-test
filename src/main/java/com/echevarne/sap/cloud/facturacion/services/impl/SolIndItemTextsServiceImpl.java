package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItemTexts;
import com.echevarne.sap.cloud.facturacion.repositories.SolIndItemTextsRepository;
import com.echevarne.sap.cloud.facturacion.services.SolIndItemTextsService;

@Service("solIndItemTextsService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SolIndItemTextsServiceImpl extends CrudServiceImpl<SolIndItemTexts, Long>
        implements SolIndItemTextsService {

    @Autowired
    public SolIndItemTextsServiceImpl(final SolIndItemTextsRepository repo) {
        super(repo);
    }
}

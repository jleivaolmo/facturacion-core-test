package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesInterlocutores;
import com.echevarne.sap.cloud.facturacion.repositories.PetMuesInterlocutoresRep;
import com.echevarne.sap.cloud.facturacion.services.PetMuesInterlocutoresService;

@Service("petMuesInterlocutoresSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PetMuesInterlocutoresServiceImpl extends CrudServiceImpl<PetMuesInterlocutores, Long>
        implements PetMuesInterlocutoresService {

    @Autowired
    public PetMuesInterlocutoresServiceImpl(final PetMuesInterlocutoresRep petMuesInterlocutoresRep) {
        super(petMuesInterlocutoresRep);
    }
}

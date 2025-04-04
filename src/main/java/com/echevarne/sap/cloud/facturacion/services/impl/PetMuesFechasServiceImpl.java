package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesFechas;
import com.echevarne.sap.cloud.facturacion.repositories.PetMuesFechasRep;
import com.echevarne.sap.cloud.facturacion.services.PetMuesFechasService;

@Service("petMuesFechasSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PetMuesFechasServiceImpl extends CrudServiceImpl<PetMuesFechas, Long> implements PetMuesFechasService {

    @Autowired
    public PetMuesFechasServiceImpl(final PetMuesFechasRep petMuesFechasRep){
        super(petMuesFechasRep);
    }

}

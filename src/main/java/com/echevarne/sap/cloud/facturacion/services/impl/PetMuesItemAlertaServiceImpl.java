package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesItemAlerta;
import com.echevarne.sap.cloud.facturacion.repositories.PetMuesItemAlertaRep;
import com.echevarne.sap.cloud.facturacion.services.PetMuesItemAlertaService;

@Service("petMuesItemAlertaSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PetMuesItemAlertaServiceImpl extends CrudServiceImpl<PetMuesItemAlerta, Long>
        implements PetMuesItemAlertaService {

    @Autowired
    public PetMuesItemAlertaServiceImpl(final PetMuesItemAlertaRep petMuesItemAlertaRep){
        super(petMuesItemAlertaRep);
    }
}

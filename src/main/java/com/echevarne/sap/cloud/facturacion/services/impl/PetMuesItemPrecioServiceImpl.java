package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesItemPrecio;
import com.echevarne.sap.cloud.facturacion.repositories.PetMuesItemPrecioRep;
import com.echevarne.sap.cloud.facturacion.services.PetMuesItemPrecioService;

@Service("petMuesItemPrecioSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PetMuesItemPrecioServiceImpl extends CrudServiceImpl<PetMuesItemPrecio, Long>
        implements PetMuesItemPrecioService {

    @Autowired
    public PetMuesItemPrecioServiceImpl(final PetMuesItemPrecioRep petMuesItemPrecioRep){
        super(petMuesItemPrecioRep);
    }
}

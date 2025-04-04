package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesPrecio;
import com.echevarne.sap.cloud.facturacion.repositories.PetMuesPrecioRep;
import com.echevarne.sap.cloud.facturacion.services.PetMuesPrecioService;


@Service("petMuesPrecioSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PetMuesPrecioServiceImpl extends CrudServiceImpl<PetMuesPrecio, Long>
        implements PetMuesPrecioService {

    @Autowired
    public PetMuesPrecioServiceImpl(final PetMuesPrecioRep petMuesPrecioRep) {
        super(petMuesPrecioRep);
    }
}

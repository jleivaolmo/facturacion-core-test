package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PetMuesItemEstado;
import com.echevarne.sap.cloud.facturacion.repositories.PetMuesItemEstadoRep;
import com.echevarne.sap.cloud.facturacion.services.PetMuesItemEstadoService;

@Service("petMuesItemEstadoSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PetMuesItemEstadoServiceImpl extends CrudServiceImpl<PetMuesItemEstado, Long>
        implements PetMuesItemEstadoService {

    @Autowired
    public PetMuesItemEstadoServiceImpl(final PetMuesItemEstadoRep petMuesItemEstadoRep) {
        super(petMuesItemEstadoRep);
    }
}

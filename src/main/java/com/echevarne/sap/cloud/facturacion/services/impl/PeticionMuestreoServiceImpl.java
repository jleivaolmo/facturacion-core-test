package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import com.echevarne.sap.cloud.facturacion.repositories.PeticionMuestreoRep;
import com.echevarne.sap.cloud.facturacion.services.PeticionMuestreoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("peticionMuestreoSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PeticionMuestreoServiceImpl extends CrudServiceImpl<PeticionMuestreo, Long>
        implements PeticionMuestreoService {
    private final PeticionMuestreoRep peticionMuestreoRep;

    @Autowired
    public PeticionMuestreoServiceImpl(final PeticionMuestreoRep peticionMuestreoRep){
        super(peticionMuestreoRep);
        this.peticionMuestreoRep = peticionMuestreoRep;
    }

    @Override
    public PeticionMuestreo findByCodigoPeticion(String codigoPeticion) {
        return peticionMuestreoRep.findByCodigoPeticion(codigoPeticion);
    }

    @Override
    public Optional<List<PeticionMuestreo>> findByCodigosPeticion(Collection<String> codigoPeticion) {
        return peticionMuestreoRep.findAllByCodigoPeticion(codigoPeticion);
    }

}

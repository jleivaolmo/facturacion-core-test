package com.echevarne.sap.cloud.facturacion.services.impl;

import com.echevarne.sap.cloud.facturacion.model.parametrizacion.Periodicidad;
import com.echevarne.sap.cloud.facturacion.repositories.PeriodicidadRep;
import com.echevarne.sap.cloud.facturacion.services.PeriodicidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("periodicidadService")
public class PeriodicidadServiceImpl extends CrudServiceImpl<Periodicidad, Long> implements PeriodicidadService {

    private final PeriodicidadRep periodicidadRep;

    @Autowired
    public PeriodicidadServiceImpl(final PeriodicidadRep periodicidadRep){
        super(periodicidadRep);
        this.periodicidadRep = periodicidadRep;
    }

    @Override
    public Optional<Periodicidad> findByNombre(String nombre) {
        return periodicidadRep.findByNombre(nombre);
    }
}

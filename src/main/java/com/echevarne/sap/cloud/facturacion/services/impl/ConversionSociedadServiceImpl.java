package com.echevarne.sap.cloud.facturacion.services.impl;

import com.echevarne.sap.cloud.facturacion.model.conversion.ConversionSociedad;
import com.echevarne.sap.cloud.facturacion.repositories.ConversionSociedadRep;
import com.echevarne.sap.cloud.facturacion.services.ConversionSociedadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ConversionSociedadServiceImpl extends CrudServiceImpl<ConversionSociedad, Long>
        implements ConversionSociedadService {

    private final ConversionSociedadRep repo;

    @Autowired
    public ConversionSociedadServiceImpl(final ConversionSociedadRep repo) {
        super(repo);
        this.repo = repo;
    }

    @Override
    public Optional<ConversionSociedad> findByParams(String cliente, String codigoDelegacion,
                                                     String prueba, String sector) {
        return repo.findByClienteAndCodigoDelegacionAndPruebaAndSector(cliente, codigoDelegacion, prueba, sector);
    }
}

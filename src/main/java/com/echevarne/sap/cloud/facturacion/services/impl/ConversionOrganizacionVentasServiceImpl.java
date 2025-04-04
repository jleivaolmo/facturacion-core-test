package com.echevarne.sap.cloud.facturacion.services.impl;

import com.echevarne.sap.cloud.facturacion.model.conversion.ConversionOrganizacionVentas;
import com.echevarne.sap.cloud.facturacion.repositories.ConversionOrganizacionVentasRep;
import com.echevarne.sap.cloud.facturacion.services.ConversionOrganizacionVentasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ConversionOrganizacionVentasServiceImpl extends CrudServiceImpl<ConversionOrganizacionVentas, Long>
        implements ConversionOrganizacionVentasService {

    private final ConversionOrganizacionVentasRep repo;

    @Autowired
    public ConversionOrganizacionVentasServiceImpl(final ConversionOrganizacionVentasRep repo){
        super(repo);
        this.repo = repo;
    }

    @Override
    public Optional<ConversionOrganizacionVentas> findByParams(String sector, String codigoDelegacion) {
        return repo.findBySectorAndCodigoDelegacion(sector, codigoDelegacion);
    }
}

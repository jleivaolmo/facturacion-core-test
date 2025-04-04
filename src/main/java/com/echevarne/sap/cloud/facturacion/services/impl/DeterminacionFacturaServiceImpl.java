package com.echevarne.sap.cloud.facturacion.services.impl;

import com.echevarne.sap.cloud.facturacion.model.salidas.DeterminacionFactura;
import com.echevarne.sap.cloud.facturacion.repositories.DeterminacionFacturaRep;
import com.echevarne.sap.cloud.facturacion.services.DeterminacionFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("determinacionFacturaSrv")
public class DeterminacionFacturaServiceImpl implements DeterminacionFacturaService {

    private DeterminacionFacturaRep determinacionFacturaRep;

    @Autowired
    public DeterminacionFacturaServiceImpl(DeterminacionFacturaRep determinacionFacturaRep) {
        this.determinacionFacturaRep = determinacionFacturaRep;
    }

    @Override
    public boolean existsDeterminacionFactura(Long id) {
        return determinacionFacturaRep.existsById(id);
    }

    @Override
    public List<DeterminacionFactura> findAll() {
        return determinacionFacturaRep.findAll();
    }
}

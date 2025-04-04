package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.salidas.DeterminacionFactura;

import java.util.List;

public interface DeterminacionFacturaService {

    boolean existsDeterminacionFactura(Long id);

    List<DeterminacionFactura> findAll();

}

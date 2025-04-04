package com.echevarne.sap.cloud.facturacion.services;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.replicated.S4Material;

public interface S4MaterialsService {
    
    Optional<S4Material> findByMATNR(String matnr);

}
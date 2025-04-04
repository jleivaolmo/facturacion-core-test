package com.echevarne.sap.cloud.facturacion.services;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.replicated.S4MaterialTexts;

public interface S4MaterialsTextService {
    
    Optional<S4MaterialTexts> findByMATNR(String matnr);

}
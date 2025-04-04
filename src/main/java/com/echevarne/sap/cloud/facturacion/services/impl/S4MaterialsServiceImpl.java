package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.replicated.S4Material;
import com.echevarne.sap.cloud.facturacion.repositories.S4MaterialsRep;
import com.echevarne.sap.cloud.facturacion.services.S4MaterialsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("s4MaterialsService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class S4MaterialsServiceImpl implements S4MaterialsService {
    
    @Autowired
    private S4MaterialsRep s4CustomersRep;

    @Override
    public Optional<S4Material> findByMATNR(String matnr) {
        return s4CustomersRep.findByMATNR(matnr);
    }

}

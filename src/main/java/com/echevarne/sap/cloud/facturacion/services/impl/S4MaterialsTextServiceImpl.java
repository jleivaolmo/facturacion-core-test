package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.model.replicated.S4MaterialTexts;
import com.echevarne.sap.cloud.facturacion.repositories.S4MaterialsTextRep;
import com.echevarne.sap.cloud.facturacion.services.S4MaterialsTextService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("s4MaterialsTextService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class S4MaterialsTextServiceImpl implements S4MaterialsTextService {
    
    @Autowired
    private S4MaterialsTextRep s4MaterialsTextRep;

    @Override
    public Optional<S4MaterialTexts> findByMATNR(String matnr) {
        return s4MaterialsTextRep.findByMATNRAndSPRAS(matnr, ConstFacturacion.IDIOMA_DEFAULT);
    }

}
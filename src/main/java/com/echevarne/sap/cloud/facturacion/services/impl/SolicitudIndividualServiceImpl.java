package com.echevarne.sap.cloud.facturacion.services.impl;

import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.repositories.SolicitudIndividualRepository;
import com.echevarne.sap.cloud.facturacion.services.SolicitudIndividualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("solicitudIndividualSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SolicitudIndividualServiceImpl extends CrudServiceImpl<SolicitudIndividual, Long> implements SolicitudIndividualService {

    private final SolicitudIndividualRepository solicitudIndividualRepository;

    @Autowired
    public SolicitudIndividualServiceImpl(final SolicitudIndividualRepository solicitudIndividualRepository){
        super(solicitudIndividualRepository);
        this.solicitudIndividualRepository = solicitudIndividualRepository;
    }

    @Override
    public Optional<SolicitudIndividual> findByCodigoPeticion(String codigoPeticion) {
        return solicitudIndividualRepository.findByPurchaseOrderByCustomer(codigoPeticion);
    }

    @Override
    public Optional<List<SolicitudIndividual>> findAllBySoldToParty(String soldToParty) {
        return solicitudIndividualRepository.findAllBySoldToParty(soldToParty);
    }
}

package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;

import java.util.List;
import java.util.Optional;

public interface SolicitudIndividualService extends CrudService<SolicitudIndividual, Long> {

    Optional<SolicitudIndividual> findByCodigoPeticion(String codigoPeticion);

    Optional<List<SolicitudIndividual>> findAllBySoldToParty(String soldToParty);
}

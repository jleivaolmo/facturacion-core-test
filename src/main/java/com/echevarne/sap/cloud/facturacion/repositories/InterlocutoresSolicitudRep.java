package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.views.InterlocutoresSolicitud;

@Repository("InterlocutoresSolicitudRep")
public interface InterlocutoresSolicitudRep extends JpaRepository<InterlocutoresSolicitud, Long> {

}

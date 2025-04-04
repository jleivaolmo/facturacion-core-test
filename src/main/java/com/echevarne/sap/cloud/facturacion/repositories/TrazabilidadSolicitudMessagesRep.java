package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudMessages;

@Repository("TrzSolMessagesRep")
public interface TrazabilidadSolicitudMessagesRep extends JpaRepository<TrazabilidadSolicitudMessages, Long> {

}

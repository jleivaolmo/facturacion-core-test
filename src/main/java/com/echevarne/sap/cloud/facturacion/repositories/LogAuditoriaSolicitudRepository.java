package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.logAuditoria.LogAuditoriaSolicitud;

@Repository("logAuditoriaSolicitudRepository")
public interface LogAuditoriaSolicitudRepository extends JpaRepository<LogAuditoriaSolicitud, Long> {

	List<LogAuditoriaSolicitud> findByFacturaAndPruebaAndCodPeticion(String factura, String prueba, String codPeticion);
}

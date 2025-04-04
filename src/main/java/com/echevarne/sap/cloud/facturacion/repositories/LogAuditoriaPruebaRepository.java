package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.logAuditoria.LogAuditoriaPrueba;

@Repository("logAuditoriaPruebaRepository")
public interface LogAuditoriaPruebaRepository extends JpaRepository<LogAuditoriaPrueba, Long> {
}

package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.logAuditoria.LogAuditoriaFacturacion;

@Repository("logAuditoriaFacturacionRepository")
public interface LogAuditoriaFacturacionRepository extends JpaRepository<LogAuditoriaFacturacion, Long> {

	List<LogAuditoriaFacturacion> findBySalesOrder(String salesOrder);
}

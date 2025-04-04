package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.facturacion.LogRectificacion;

@Repository("logRectificacionRep")
public interface LogRectificacionRep extends JpaRepository<LogRectificacion, Long> {
}

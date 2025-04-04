package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudProcessStatus;

@Repository("TrzSolProcStatusRep")
public interface TrazabilidadSolicitudProcessStatusRep extends JpaRepository<TrazabilidadSolicitudProcessStatus, Long>{
	
}

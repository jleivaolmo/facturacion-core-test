package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudEstHistory;

/**
 * 
 * @author Hernan Girardi
 * @since 23/06/2020
 */
@Repository("trazabilidadSolEstHistoryRep")
public interface TrazabilidadSolEstHistoryRep extends JpaRepository<TrazabilidadSolicitudEstHistory, Long> {
	
	public Optional<TrazabilidadSolicitudEstHistory> findByEstado(MasDataEstado estado);
}

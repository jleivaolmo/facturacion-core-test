package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadEstHistory;

/**
 * 
 * @author Hernan Girardi
 * @since 23/06/2020
 */
@Repository("trazabilidadEstHistoryRep")
public interface TrazabilidadEstHistoryRep extends JpaRepository<TrazabilidadEstHistory, Long> {
	
	public Optional<TrazabilidadEstHistory> findByEstado(MasDataEstado estado);
	
	@Query(value = "UPDATE TrazabilidadEstHistory r SET r.inactive = true WHERE r.trazabilidad.id = :id", nativeQuery = false)
	void desactivarEstados(Long id);
	
}
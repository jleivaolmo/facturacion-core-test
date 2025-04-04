package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadAlbaranEstHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Germán Laso
 * @since 15/02/2021
 */
@Repository("trazabilidadAlbEstHistoryRep")
public interface TrazabilidadAlbEstHistoryRep extends JpaRepository<TrazabilidadAlbaranEstHistory, Long> {
	
	public Optional<TrazabilidadAlbaranEstHistory> findByEstado(MasDataEstado estado);
	
}

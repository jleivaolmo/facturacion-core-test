package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolAgrEstHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author David Bolet
 * @version 1.0
 * @since 28/04/2020
 *
 */
@Repository("trazabilidadSolAgrEstHistoryRepository")
public interface TrazabilidadSolAgrEstHistoryRepository extends JpaRepository<TrazabilidadSolAgrEstHistory, Long> {
}
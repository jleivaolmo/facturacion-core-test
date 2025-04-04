package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolAgrItems;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolAgrItemsEstHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author David Bolet
 * @version 1.0
 * @since 28/04/2020
 *
 */
@Repository("trazabilidadSolAgrItemsEstHistoryRepository")
public interface TrazabilidadSolAgrItemsEstHistoryRepository extends JpaRepository<TrazabilidadSolAgrItemsEstHistory, Long> {


}

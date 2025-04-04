package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudAgrupado;

/**
 * @author Steven Mendez
 * @version 1.0
 * @since 09/02/2019
 *
 */
@Repository("trazabilidadSolAgrRepository")
public interface TrazabilidadSolAgrRepository extends JpaRepository<TrazabilidadSolicitudAgrupado, Long> {
	TrazabilidadSolicitudAgrupado findById(String id);
	
	Optional<List<TrazabilidadSolicitudAgrupado>> findAllBySalesOrder(String salesOrder);
	
	Optional<List<TrazabilidadSolicitudAgrupado>> findAllByBillingDocument(String billingDocument);
		
	Optional<List<TrazabilidadSolicitudAgrupado>> findAllByTrazabilidadOrderByEntityCreationTimestamp(TrazabilidadSolicitud traza);

}

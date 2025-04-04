package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitudAgrupado;

/**
 * @author David Bolet
 * @version 1.0
 * @since 28/04/2020
 *
 */
@Repository("trazabilidadSolicitudAgrupadaRepository")
public interface TrazabilidadSolicitudAgrupadaRepository extends JpaRepository<TrazabilidadSolicitudAgrupado, Long> {
	
	Optional<List<TrazabilidadSolicitudAgrupado>> findAllByBillingDocument(String billingDocument);
}

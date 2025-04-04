package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.List;
import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;

/**
 * @author Steven Mendez
 * @version 1.0
 * @since 09/02/2019
 *
 */
@Repository("trazabilidadSolicitudRepository")
public interface TrazabilidadSolicitudRepository extends JpaRepository<TrazabilidadSolicitud, Long> {

	TrazabilidadSolicitud findById(String id);

	TrazabilidadSolicitud findByPeticionRec(PeticionMuestreo peticionMuestreo);

}

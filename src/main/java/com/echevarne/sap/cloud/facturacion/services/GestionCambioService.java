package com.echevarne.sap.cloud.facturacion.services;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.gestioncambios.EntityComparatorResult;
import com.echevarne.sap.cloud.facturacion.model.GestionCambio;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.SolicitudMuestreo;

/**
 * Interface for the service{@link GestionCambioService}.
 * 
 * <p>This is a interface for Services. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
public interface GestionCambioService extends CrudService<GestionCambio, Long> {

  EntityComparatorResult compare(Object entity1, Object entity2);

  SolicitudMuestreo process(SolicitudMuestreo oldSolicitudMuestreo, SolicitudMuestreo newSolicitudMuestreo) throws Exception;

  // PeticionMuestreo process(PeticionMuestreo oldPeticionMuestreo, PeticionMuestreo newPeticionMuestreo) throws Exception;
  
  Optional<GestionCambio> findByEntityId(Long entityId);

}

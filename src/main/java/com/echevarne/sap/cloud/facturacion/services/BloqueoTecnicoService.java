package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import com.echevarne.sap.cloud.facturacion.model.actualizacion.BloqueoTecnico;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;

import java.util.List;
import java.util.Optional;

/**
 * @author David Bolet
 * @version 1.0
 * @since 17/09/2021
 */
public interface BloqueoTecnicoService extends CrudService<BloqueoTecnico, Long> {

    List<BloqueoTecnico> findBloqueosActivos();
    
    List<BloqueoTecnico> findBloqueosActivosPorUUIDInstance(String uuidInstance);

    BloqueoTecnico bloquearPeticion(PeticionMuestreo peticion);

    BloqueoTecnico bloquearSolicitudIndividual(SolicitudIndividual solicitudIndividual);

    List<BloqueoTecnico> crearBloqueoMultiple(List<PeticionMuestreo> peticiones);

    BloqueoTecnico crearBloqueo(TrazabilidadSolicitud trazabilidadSolicitud);

    Optional<BloqueoTecnico> getByTrazabilidad(TrazabilidadSolicitud trazabilidadSolicitud);

    boolean tieneBloqueoTecnico(TrazabilidadSolicitud trazabilidadSolicitud);

    boolean tieneBloqueoTecnico(SolicitudIndividual si);

    boolean tieneBloqueoTecnico(PeticionMuestreo peticion);

    void desbloquear(TrazabilidadSolicitud trazabilidadSolicitud);
    
    void desbloquear(long idBloqueoTecnico);

    void desbloquearPeticion(PeticionMuestreo peticion);

    void desbloquearMultiple(Iterable<SolicitudIndividual> solicitudes);

}

package com.echevarne.sap.cloud.facturacion.services;

import java.util.Collection;
import java.util.List;
import com.echevarne.sap.cloud.facturacion.model.actualizacion.ProcesoActualizacionEjecucion;

/**
 * @author David Bolet
 * @version 1.0
 * @since 21/09/2021
 */
public interface ProcesoActualizacionEjecucionService extends CrudService<ProcesoActualizacionEjecucion, Long> {

    ProcesoActualizacionEjecucion programarEjecucion(Long idProcesoActualizacion);

    ProcesoActualizacionEjecucion comenzar(String uuid, Integer totalElementos, String instanceUUID);

    ProcesoActualizacionEjecucion comenzarEjecucionPuntual(Integer totalElementos, String queryParams, String instanceUUID);

    void actualizar(String uuid, String codigoPeticion, Long duration);

    void actualizarError(String uuid, String codigoPeticion, String errorMessage);

    ProcesoActualizacionEjecucion finalizar(String processUUID);

    ProcesoActualizacionEjecucion findByProcessId(String processId);
    
    List<ProcesoActualizacionEjecucion> findByUUIDInstance(String processId);

    Collection<ProcesoActualizacionEjecucion> getActives();

    List<ProcesoActualizacionEjecucion> findScheduled();
}

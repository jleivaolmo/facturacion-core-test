package com.echevarne.sap.cloud.facturacion.repositories;


import com.echevarne.sap.cloud.facturacion.model.actualizacion.ProcesoActualizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for the Model {@link ProcesoActualizacionRep}
 * @author David Bolet
 * @since 22/09/2021
 */

@Repository("procesoActualizacionRep")
public interface ProcesoActualizacionRep extends JpaRepository<ProcesoActualizacion, Long> {

    List<ProcesoActualizacion> findByEjecucionInmediata(Boolean ejecucionInmediata);
}

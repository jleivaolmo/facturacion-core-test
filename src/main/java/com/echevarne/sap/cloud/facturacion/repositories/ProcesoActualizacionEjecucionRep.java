package com.echevarne.sap.cloud.facturacion.repositories;

import com.echevarne.sap.cloud.facturacion.model.actualizacion.ProcesoActualizacionEjecucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository("procesoActualizacionEjecucionRep")
public interface ProcesoActualizacionEjecucionRep extends JpaRepository<ProcesoActualizacionEjecucion, Long> {

    Optional<ProcesoActualizacionEjecucion> findByProcessId(String processId);

    List<ProcesoActualizacionEjecucion> findByEstado(Integer estadoPlanificado);
    
    @Query("SELECT p FROM ProcesoActualizacionEjecucion p WHERE uuidInstance = ?1 AND p.estado = 2")
	List<ProcesoActualizacionEjecucion> findByUUIDInstance(String UUIDInstance);
}

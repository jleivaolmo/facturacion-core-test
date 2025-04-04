package com.echevarne.sap.cloud.facturacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.echevarne.sap.cloud.facturacion.model.actualizacion.ProcesoActualizacionEjecucion;
import com.echevarne.sap.cloud.facturacion.model.actualizacion.ProcesoActualizacionLog;

@Repository("procesoActualizacionLogRep")
public interface ProcesoActualizacionLogRep extends JpaRepository<ProcesoActualizacionLog, Long> {

    @Modifying
    @Query("update ProcesoActualizacionLog pal set pal.procesoActualizacionEjecucion = ?1 where pal.idProceso = ?2")
    void setProcesoEjecucion(ProcesoActualizacionEjecucion procesoFinal, String processId);

}

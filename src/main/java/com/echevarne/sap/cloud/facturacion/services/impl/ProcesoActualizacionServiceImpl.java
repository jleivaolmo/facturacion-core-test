package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import com.echevarne.sap.cloud.facturacion.model.actualizacion.ProcesoActualizacion;
import com.echevarne.sap.cloud.facturacion.model.parametrizacion.Periodicidad;
import com.echevarne.sap.cloud.facturacion.repositories.ProcesoActualizacionRep;
import com.echevarne.sap.cloud.facturacion.services.PeriodicidadService;
import com.echevarne.sap.cloud.facturacion.services.ProcesoActualizacionEjecucionService;
import com.echevarne.sap.cloud.facturacion.services.ProcesoActualizacionService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("procesoActualizacionService")
public class ProcesoActualizacionServiceImpl extends CrudServiceImpl<ProcesoActualizacion, Long>
        implements ProcesoActualizacionService {

    private final ProcesoActualizacionRep procesoActualizacionRep;
    private final ProcesoActualizacionEjecucionService procesoActualizacionEjecucionService;
    
    @Autowired
    PeriodicidadService periodicidadSrv;

    @Autowired
    public ProcesoActualizacionServiceImpl(
            final ProcesoActualizacionRep procesoActualizacionRep,
            final ProcesoActualizacionEjecucionService procesoActualizacionEjecucionService) {
        super(procesoActualizacionRep);
        this.procesoActualizacionRep = procesoActualizacionRep;
        this.procesoActualizacionEjecucionService = procesoActualizacionEjecucionService;
    }

    @Override
    @Transactional
    public ProcesoActualizacion create(ProcesoActualizacion entity) {
        var createdEntity = super.create(entity);
        procesoActualizacionEjecucionService.programarEjecucion(createdEntity.getId());
        return createdEntity;
    }

    @Override
    public List<ProcesoActualizacion> findNoInmediatas() {
        return procesoActualizacionRep.findByEjecucionInmediata(false);
    }
    
    @Override
    @Transactional
    public void setNuevaFechaInicio(Long idProcAct) {
    	var procesoActualizacion = this.findById(idProcAct).get();
    	var idPeriodicidad = procesoActualizacion.getIdPeriodicidad();
		Periodicidad periodicidad = periodicidadSrv.findById(idPeriodicidad).get();
		var oldDate = procesoActualizacion.getFechaInicio();
		oldDate.add(Calendar.MINUTE, periodicidad.getNumMinutos());
		procesoActualizacion.setFechaInicio(oldDate);
    }
}

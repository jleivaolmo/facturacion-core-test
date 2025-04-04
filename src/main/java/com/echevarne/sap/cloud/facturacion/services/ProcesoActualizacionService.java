package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.actualizacion.ProcesoActualizacion;
import java.util.List;

public interface ProcesoActualizacionService extends CrudService<ProcesoActualizacion, Long> {
    List<ProcesoActualizacion> findNoInmediatas();
	public void setNuevaFechaInicio(Long idProcAct);
}

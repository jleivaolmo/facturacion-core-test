package com.echevarne.sap.cloud.facturacion.services;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadClasificacion;

public interface TrazabilidadClasificacionService extends CrudService<TrazabilidadClasificacion, Long>{

	Optional<TrazabilidadClasificacion> findByTrazabilidadPrueba(Trazabilidad trazabilidadPrueba);
}

package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.parametrizacion.Periodicidad;

import java.util.Optional;

public interface PeriodicidadService extends CrudService<Periodicidad, Long> {

    Optional<Periodicidad> findByNombre(String nombre);
}

package com.echevarne.sap.cloud.facturacion.services;

import java.util.Optional;

import com.echevarne.sap.cloud.facturacion.model.liquidaciones.TramosLiqNumPruebas;

public interface TramosLiqNumPruebasService extends CrudService<TramosLiqNumPruebas, Long> {

	Optional<TramosLiqNumPruebas> findByNumPruebas(int numPruebas);
                                
}

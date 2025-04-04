package com.echevarne.sap.cloud.facturacion.services;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.echevarne.sap.cloud.facturacion.model.determinaciones.FacturableFechaPeticion;

public interface FacturableFechaPeticionService extends CrudService<FacturableFechaPeticion, Long> {

    List<FacturableFechaPeticion> findByParams(Collection<String> organizaciones, Collection<String> clientes,
                                       Collection<String> oficinas, Collection<Integer> tipos, Collection<String> empresas,
                                       Collection<String> companias, Collection<String> remitentes, Calendar fechaValidez);
                                       
}

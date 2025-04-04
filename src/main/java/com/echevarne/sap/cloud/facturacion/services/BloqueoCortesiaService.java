package com.echevarne.sap.cloud.facturacion.services;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.echevarne.sap.cloud.facturacion.model.determinaciones.BloqueoCortesia;

public interface BloqueoCortesiaService extends CrudService<BloqueoCortesia, Long> {

    List<BloqueoCortesia> findByParams(Collection<String> organizaciones, Collection<String> clientes,
                                       Collection<String> oficinas, Collection<Integer> tipos, Collection<String> empresas,
                                       Collection<String> companias, Collection<String> remitentes, Calendar fechaValidez);
                                       
}

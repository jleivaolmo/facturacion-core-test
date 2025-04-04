package com.echevarne.sap.cloud.facturacion.services;

import com.echevarne.sap.cloud.facturacion.model.salidas.MonitorizacionSalida;

import java.util.Date;
import java.util.List;

public interface MonitorizacionSalidaService {

    MonitorizacionSalida create(MonitorizacionSalida monitorizacionSalida);

    List<String> getFacturasPendientes();

    List<String> getFacturasPendientes(Date startDate);

}

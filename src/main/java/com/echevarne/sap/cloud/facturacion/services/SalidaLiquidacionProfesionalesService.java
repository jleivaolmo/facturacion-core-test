package com.echevarne.sap.cloud.facturacion.services;

import java.util.List;

import com.echevarne.sap.cloud.facturacion.model.salidas.SalidaLiquidacionProfesionales;

public interface SalidaLiquidacionProfesionalesService {

	List<SalidaLiquidacionProfesionales> findAll();
}

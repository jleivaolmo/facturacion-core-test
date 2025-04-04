package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.salidas.SalidaLiquidacionProfesionales;
import com.echevarne.sap.cloud.facturacion.repositories.SalidaLiquidacionProfesionalesRep;
import com.echevarne.sap.cloud.facturacion.services.SalidaLiquidacionProfesionalesService;

@Service("salidaLiquidacionProfesionalesService")
@Transactional(readOnly = true)
public class SalidaLiquidacionProfesionalesServiceImpl implements SalidaLiquidacionProfesionalesService {

	@Autowired
	private SalidaLiquidacionProfesionalesRep salidaLiquidacionProfesionalesRep;

	public List<SalidaLiquidacionProfesionales> findAll() {
		return salidaLiquidacionProfesionalesRep.findAll();
	}
}

package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.salidas.SalidaLiquidacionRemitentes;
import com.echevarne.sap.cloud.facturacion.repositories.SalidaLiquidacionRemitentesRep;
import com.echevarne.sap.cloud.facturacion.services.SalidaLiquidacionRemitentesService;

@Service("salidaLiquidacionRemitentesService")
@Transactional(readOnly = true)
public class SalidaLiquidacionRemitentesServiceImpl implements SalidaLiquidacionRemitentesService {

	@Autowired
	private SalidaLiquidacionRemitentesRep salidaLiquidacionRemitentesRep;

	@Override
	public List<SalidaLiquidacionRemitentes> findAll() {
		return salidaLiquidacionRemitentesRep.findAll();
	}
}

package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.EstadoLiquidacionPeticion;
import com.echevarne.sap.cloud.facturacion.repositories.EstadoLiquidacionPeticionRep;
import com.echevarne.sap.cloud.facturacion.services.EstadoLiquidacionPeticionService;

@Service("estadoLiquidacionPeticionSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class EstadoLiquidacionPeticionServiceImpl extends CrudServiceImpl<EstadoLiquidacionPeticion, Long>
		implements EstadoLiquidacionPeticionService {

	@Autowired
	public EstadoLiquidacionPeticionServiceImpl(final EstadoLiquidacionPeticionRep estadoLiquidacionRep) {
		super(estadoLiquidacionRep);
	}
}

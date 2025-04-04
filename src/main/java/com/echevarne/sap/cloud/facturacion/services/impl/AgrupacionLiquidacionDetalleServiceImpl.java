package com.echevarne.sap.cloud.facturacion.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.AgrupacionLiquidacionDetalle;
import com.echevarne.sap.cloud.facturacion.repositories.AgrupacionLiquidacionDetalleRep;
import com.echevarne.sap.cloud.facturacion.services.AgrupacionLiquidacionDetalleService;

@Service("agrupacionLiquidacionDetalleSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AgrupacionLiquidacionDetalleServiceImpl extends CrudServiceImpl<AgrupacionLiquidacionDetalle, Long> implements AgrupacionLiquidacionDetalleService {

	@SuppressWarnings("unused")
	private final AgrupacionLiquidacionDetalleRep agrupacionLiquidacionDetalleRep;
	
	@Autowired
	public AgrupacionLiquidacionDetalleServiceImpl(final AgrupacionLiquidacionDetalleRep agrupacionLiquidacionDetalleRep) {
		super(agrupacionLiquidacionDetalleRep);
		this.agrupacionLiquidacionDetalleRep = agrupacionLiquidacionDetalleRep;
	}

}

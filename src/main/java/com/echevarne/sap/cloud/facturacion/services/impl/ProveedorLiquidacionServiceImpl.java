package com.echevarne.sap.cloud.facturacion.services.impl;

import java.util.Calendar;
import java.util.List;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.ProveedorLiquidacion;
import com.echevarne.sap.cloud.facturacion.repositories.ProveedorLiquidacionRep;
import com.echevarne.sap.cloud.facturacion.services.ProveedorLiquidacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("proveedorLiquidacionSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProveedorLiquidacionServiceImpl extends CrudServiceImpl<ProveedorLiquidacion, Long>
		implements ProveedorLiquidacionService {
	
	private final ProveedorLiquidacionRep proveedorLiquidacionRep;

	@Autowired
	public ProveedorLiquidacionServiceImpl(final ProveedorLiquidacionRep proveedorLiquidacionRep) {
		super(proveedorLiquidacionRep);
		this.proveedorLiquidacionRep = proveedorLiquidacionRep;
	}

	@Override
	public List<ProveedorLiquidacion> findSolapamientos(String organizacionVentas, String delegacion,
			String remitenteProfesional, String sector, Calendar fechaIni, Calendar fechaFin) {
		return proveedorLiquidacionRep.findSolapamientos(organizacionVentas, delegacion, remitenteProfesional, sector,
				fechaIni, fechaFin);
	}
	
	@Override
	public List<ProveedorLiquidacion> findByProveedor(String organizacionVentas, String idProveedorLiquidacion) {
		return proveedorLiquidacionRep.findByProveedor(organizacionVentas, idProveedorLiquidacion);
	}
}
